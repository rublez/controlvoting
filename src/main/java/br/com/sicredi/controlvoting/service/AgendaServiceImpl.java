package br.com.sicredi.controlvoting.service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import br.com.sicredi.controlvoting.dto.AgendaRequest;
import br.com.sicredi.controlvoting.dto.AgendaResponse;
import br.com.sicredi.controlvoting.entity.Agenda;
import br.com.sicredi.controlvoting.exception.DataNotFoundException;
import br.com.sicredi.controlvoting.mappers.AgendaMapper;
import br.com.sicredi.controlvoting.repository.AgendaRepository;
import br.com.sicredi.controlvoting.repository.AgendaRepositoryCustom;

@Service
public class AgendaServiceImpl implements AgendaService {
	private final Logger log;
	private final Validator validator;
	private final AgendaRepository agendaRepository;
	private final AgendaRepositoryCustom agendaRepositoryCustom;
	private final ScheduledAgendaService scheduledAgendas;

	public AgendaServiceImpl(Logger log, Validator validator, AgendaRepository agendaRepository,
			AgendaRepositoryCustom agendaRepositoryCustom, ScheduledAgendaService scheduledAgendas) {
		this.log = log;
		this.validator = validator;
		this.agendaRepository = agendaRepository;
		this.agendaRepositoryCustom = agendaRepositoryCustom;
		this.scheduledAgendas = scheduledAgendas;

	}

	@Override
	public AgendaResponse create(final AgendaRequest agendaRequest) {
		validateMandatoryFields(agendaRequest);

		String idAgenda = agendaExist(agendaRequest.getAgendaName());
		if (null != idAgenda) {
			throw new DataNotFoundException(String.format("Agenda already exists: %s", idAgenda));

		}
		final Agenda agenda = agendaRepository.save(AgendaMapper.INSTANCE.convertFromRequest(agendaRequest));
		log.info("New agenda created: {}", agenda);

		scheduleNewAgenda(agenda);
		
		return AgendaMapper.INSTANCE.convertToAgendaResponse(agenda);

	}

	@Override
	public List<AgendaResponse> list(final Boolean listAll) {
		return AgendaMapper.INSTANCE.convertToAgendaResponses(agendaRepositoryCustom.listAgendas(listAll));

	}

	private void scheduleNewAgenda(final Agenda agenda) {
		String idAgenda = agenda.getIdAgenda();
		String agendaName = agenda.getAgendaName();
		
		// Schedule agenda's start
		Date scheduleTo = Date.from(agenda.getVotingStart().atZone(ZoneId.systemDefault()).toInstant());
		scheduledAgendas.schedule(agendaName, scheduleTo, idAgenda, Boolean.TRUE);

		// Schedule agenda's finish. Added 1 minute on finish time to all votes get processed
		Date scheduleFinish = Date.from((agenda.getVotingStart().plusMinutes(agenda.getVotationOpenFor() + 1L)
				.atZone(ZoneId.systemDefault()).toInstant()));
		
		scheduledAgendas.schedule(agendaName, scheduleFinish, idAgenda, Boolean.FALSE);
		log.info("New agenda scheduled. Id: {}", idAgenda);

	}

	private String agendaExist(final String agendaName) {
		// Did this way to get the ID of existing Agenda
		Agenda agenda = agendaRepository.findOneByAgendaName(agendaName);

		return null != agenda ? agenda.getIdAgenda() : null;

	}

	private void validateMandatoryFields(final Object object) {
		final Set<ConstraintViolation<Object>> violations = validator.validate(object);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);

		}

	}

}
