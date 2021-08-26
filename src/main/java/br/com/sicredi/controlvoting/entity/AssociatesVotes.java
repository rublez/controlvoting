package br.com.sicredi.controlvoting.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "associatesVotes")
public class AssociatesVotes {

	@Id
	private String idAssociateVoted;
	
	private String cpf;
	
	private String idAgenda;
	
	private Instant voted;

	public AssociatesVotes(String cpf, String idAgenda, Instant voted) {
		this.cpf = cpf;
		this.idAgenda = idAgenda;
		this.voted = voted;
		
	}
	
	
	
	
}
