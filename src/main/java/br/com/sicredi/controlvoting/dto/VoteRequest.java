package br.com.sicredi.controlvoting.dto;

import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.sicredi.controlvoting.util.CustomInstantDeserializer;
import br.com.sicredi.controlvoting.util.CustomInstantSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class VoteRequest {

	@NotBlank(message = "Associate CPF must be informed")
	@CPF(message = "CPF informed is invalid")
	private String cpfVoter;

	@NotBlank(message = "Agenda Id must be informed")
	private String idAgenda;

	@NotNull(message = "Your vote must be informed")
	private Boolean agreeWithAgenda;

	@JsonDeserialize(using = CustomInstantDeserializer.class)
	@JsonSerialize(using = CustomInstantSerializer.class)
	@Builder.Default
	private Instant whenVoteReceived = Instant.now();

}
