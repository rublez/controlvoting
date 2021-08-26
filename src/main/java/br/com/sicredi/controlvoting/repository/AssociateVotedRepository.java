package br.com.sicredi.controlvoting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.sicredi.controlvoting.entity.AssociatesVotes;

@Repository
public interface AssociateVotedRepository extends MongoRepository<AssociatesVotes, String> {

	AssociatesVotes findOneByCpfAndIdAgenda(final String cpf, final String idAgenda);
	
}
