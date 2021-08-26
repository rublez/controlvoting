package br.com.sicredi.controlvoting.dto;

import java.time.Instant;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteResponse {

	private String agenda;

	private Boolean agreeWithAgenda;

	@JsonSerialize(using = ToStringSerializer.class)
	private Instant whenVoteReceived;

	private Boolean voteAccepted;
	
}
