package br.com.sicredi.controlvoting.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.sicredi.controlvoting.broker.VotingResultSend;
import br.com.sicredi.controlvoting.dto.VoteRequest;
import br.com.sicredi.controlvoting.dto.VoteResponse;
import br.com.sicredi.controlvoting.dto.VotesTotalizedMessage;
import br.com.sicredi.controlvoting.entity.Agenda;
import br.com.sicredi.controlvoting.entity.AssociatesVotes;
import br.com.sicredi.controlvoting.exception.BadRequestException;
import br.com.sicredi.controlvoting.exception.DataNotFoundException;
import br.com.sicredi.controlvoting.integration.CheckCpfClient;
import br.com.sicredi.controlvoting.repository.AgendaRepository;
import br.com.sicredi.controlvoting.repository.AgendaRepositoryCustom;
import br.com.sicredi.controlvoting.repository.AssociateVotedRepository;

@Service
public class VoteServiceImpl implements VoteService {
	private final Logger log;
	private final AgendaRepository agendaRepository;
	private final AgendaRepositoryCustom agendaRepositoryCustom;
	private final AssociateVotedRepository associateVotedRepository;
	private final CheckCpfClient checkCpfClient;
	private final VotingResultSend votingResultSend;

	private static String ABLE = "{\"status\":\"ABLE_TO_VOTE\"}";

	public VoteServiceImpl(Logger log, AgendaRepository agendaRepository, AgendaRepositoryCustom agendaRepositoryCustom,
			CheckCpfClient checkCpfClient, AssociateVotedRepository associateVotedRepository,
			VotingResultSend votingResultSend) {
		this.log = log;
		this.agendaRepository = agendaRepository;
		this.agendaRepositoryCustom = agendaRepositoryCustom;
		this.associateVotedRepository = associateVotedRepository;
		this.checkCpfClient = checkCpfClient;
		this.votingResultSend = votingResultSend;

	}

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public VoteResponse vote(final VoteRequest voteRequest) {
		VoteResponse voteResponse = null;
		String message = "This agenda is not active or no longer accepting votes.";
		String idAgenda = voteRequest.getIdAgenda();
		Agenda agendaPersisted = agendaRepository.findOneByIdAgenda(idAgenda);
		if (null == agendaPersisted)
			throw new DataNotFoundException(String.format("Vote not registered. Agenda not found. id: %s", idAgenda));

		LocalDateTime votingStart = agendaPersisted.getVotingStart();
		LocalDateTime votingLimit = votingStart.plusMinutes(agendaPersisted.getVotationOpenFor());
		
		// Agenda is active (accepting votes) and, if not, vote arrived on time?
		if (Boolean.TRUE.equals(agendaPersisted.getActive())
				|| (LocalDateTime.now().isAfter(votingStart) && LocalDateTime
						.ofInstant(voteRequest.getWhenVoteReceived(), ZoneOffset.ofHours(-3)).isBefore(votingLimit))) {

			// Associate and vote ok?
			validateAssociate(voteRequest);

			agendaRepositoryCustom.updateVoteOnAgenda(voteRequest, agendaPersisted);

			registerAssociateVote(voteRequest.getCpfVoter(), idAgenda);
			
			voteResponse = VoteResponse.builder()
					.agreeWithAgenda(true)
					.agenda(agendaPersisted.getAgendaName())
					.voteAccepted(true)
					.whenVoteReceived(voteRequest.getWhenVoteReceived())
				.build();
			
		} else
			throw new BadRequestException(message);

		return voteResponse;

	}

	@Override
	public void totalizeVotes(String idAgenda) {
		Optional<Agenda> agendaOpt = agendaRepository.findById(idAgenda);
		Integer votesYes = 0;
		Integer votesNo = 0;
		if (agendaOpt.isPresent()) {
			Agenda agenda = agendaOpt.get();
			votesYes = agenda.getVotesYes();
			votesNo = agenda.getVotesNo();
			Boolean approved = agenda.getVotesYes() > agenda.getVotesNo();
			Integer votes = agenda.getVotesYes();
			if (Boolean.FALSE.equals(approved)) {
				votes = agenda.getVotesNo();

			}
			if (0 < votes || 0 != votesYes.compareTo(votesNo)) {
				log.info("Agenda '{}' was {}. ", agenda.getAgendaName(), approved ? "approved" : "rejected");
				VotesTotalizedMessage votesTotalizedMessage = new VotesTotalizedMessage(idAgenda,
						agenda.getAgendaName(), votes, approved, LocalDate.now());

				votingResultSend.sendTotalization(votesTotalizedMessage);

				// Agenda did not receive any vote
			} else
				log.info("Agenda '{}' did not receive any vote or there was a tie. Yes: {}, No: {}. Not sent.",
						agenda.getAgendaName(), votesYes, votesNo);

		} else
			throw new DataNotFoundException(String.format("An error happened, no agenda found: id %s", idAgenda));

	}

	private void validateAssociate(VoteRequest voteRequest) {
		String associateCpf = voteRequest.getCpfVoter();
		String idAgenda = voteRequest.getIdAgenda();
		AssociatesVotes associateVoted = associateVotedRepository.findOneByCpfAndIdAgenda(associateCpf, idAgenda);

		if (null != associateVoted)
			throw new BadRequestException(String.format("Associate CPF %s already voted in this agenda on %s",
					associateVoted.getCpf(), associateVoted.getVoted().toString()));

		try {
			String ableToVote = checkCpfClient.validateCpf(associateCpf);

			if (null != ableToVote && !ABLE.equals(ableToVote)) {
				throw new BadRequestException(
						String.format("Associate CPF %s is unable to vote", voteRequest.getCpfVoter()));

			}

		} catch (Exception e) {
			throw new DataNotFoundException(String.format("CPF %s was not found", associateCpf));

		}

	}

	private void registerAssociateVote(String cpf, String idAgenda) {
		AssociatesVotes associatesVotes = new AssociatesVotes(cpf, idAgenda, Instant.now());
		associateVotedRepository.save(associatesVotes);
		log.info("Vote has been registered: {}", associatesVotes);

	}

}
