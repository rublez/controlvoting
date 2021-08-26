package br.com.sicredi.controlvoting.dto;

import java.time.Instant;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import br.com.sicredi.controlvoting.util.CustomInstantDeserializer;
import br.com.sicredi.controlvoting.util.CustomInstantSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AgendaRequest {

	@Length(message = "The name of the agenda must have 40 characters at most", min = 5, max = 40)
	@NotEmpty(message = "The name of an agenda is mandatory")
	@JsonProperty("agenda_name")
	private String agendaName;

	@Length(message = "The name of the agenda must have 40 characters at most", min = 5, max = 100)
	private String agendaDescription;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using=LocalDateTimeSerializer.class)
	@NotNull(message = "Votation start must be informed")
	private LocalDateTime votingStart;

	// For how many time, in minutes, a votation will be open after started. If
	// null, defaults to 60 minutes.
	@Builder.Default
	private Integer votationOpenFor = 60;

	@JsonDeserialize(using = CustomInstantDeserializer.class)
	@JsonSerialize(using = CustomInstantSerializer.class)
	@Builder.Default
	private Instant created = Instant.now();
	
}
