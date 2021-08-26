package br.com.sicredi.controlvoting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.sicredi.controlvoting.entity.Agenda;

@Repository
public interface AgendaRepository extends MongoRepository<Agenda, String> {

	Agenda findOneByAgendaName(final String agendaName);

	@Query("{'_id': ?0}")
	Agenda findOneByIdAgenda(final String idAgenda);

}
