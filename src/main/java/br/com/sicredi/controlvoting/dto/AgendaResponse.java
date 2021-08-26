package br.com.sicredi.controlvoting.dto;

import java.time.Instant;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AgendaResponse {

	private String idAgenda;

	private String agendaName;
	
	private String agendaDescription;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime votingStart;
	
	private Integer votationOpenFor;
	
	private Integer votesYes;

	private Integer votesNo;
	
	private Instant created;
	
}
