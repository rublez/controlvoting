package br.com.sicredi.controlvoting.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import br.com.sicredi.controlvoting.dto.VoteRequest;
import br.com.sicredi.controlvoting.entity.Agenda;
import br.com.sicredi.controlvoting.util.Helper;

@Component
public class AgendaRepositoryCustomImpl implements AgendaRepositoryCustom {

	private final Logger log;
	private final MongoTemplate mongoTemplate;

	public AgendaRepositoryCustomImpl(Logger log, MongoTemplate mongoTemplate) {
		this.log = log;
		this.mongoTemplate = mongoTemplate;

	}

	@Override
	public List<Agenda> listAgendas(final Boolean listAll) {

		if (Boolean.TRUE.equals(listAll)) {
			return mongoTemplate.findAll(Agenda.class);

		}
		Query query = new Query(Criteria.where("startDate").gte(LocalDateTime.now()));
		return mongoTemplate.find(query, Agenda.class);

	}

	@Override
	public void updateVoteOnAgenda(final VoteRequest voteRequest, final Agenda agendaPersisted) {
		Agenda agendaClone = Helper.clone(agendaPersisted);
		Integer yes = agendaClone.getVotesYes();
		Integer no = agendaClone.getVotesNo();

		if (Boolean.TRUE.equals(voteRequest.getAgreeWithAgenda())) {
			agendaClone.setVotesYes(Integer.valueOf(yes.intValue() + 1));

		} else {
			agendaClone.setVotesNo(Integer.valueOf(no.intValue() + 1));

		}
		mongoTemplate.save(agendaClone);
		log.info("Agenda {} have got a new vote.", agendaPersisted.getAgendaName());

	}
	
}
