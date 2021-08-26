package br.com.sicredi.controlvoting.repository;

import java.util.List;

import br.com.sicredi.controlvoting.dto.VoteRequest;
import br.com.sicredi.controlvoting.entity.Agenda;

public interface AgendaRepositoryCustom {

	List<Agenda> listAgendas(final Boolean listAll);

	void updateVoteOnAgenda(final VoteRequest voteRequest, final Agenda agendaPersisted);

}
