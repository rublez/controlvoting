package br.com.sicredi.controlvoting.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "agendas")
public class Agenda implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7625743479499035399L;

	@Id
	private String idAgenda;

	@Indexed
	private String agendaName;

	private String agendaDescription;

	@Builder.Default
	private Integer votesYes = 0;

	@Builder.Default
	private Integer votesNo = 0;

	private LocalDateTime votingStart;

	private Integer votationOpenFor;

	@Builder.Default
	private Boolean active = false;

	private Instant created;

}
