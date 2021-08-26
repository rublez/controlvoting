package br.com.sicredi.controlvoting.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VotesTotalizedMessage {

	private String idAgenda;
	
	private String nomeAgenda;
	
	private Integer votes;
	
	private Boolean agendaApproved;
	
	private LocalDate votingDate;
	
}
