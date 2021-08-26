package br.com.sicredi.controlvoting.service;

import br.com.sicredi.controlvoting.dto.VoteRequest;
import br.com.sicredi.controlvoting.dto.VoteResponse;

public interface VoteService {

	/**
	 * Receives a vote
	 * 
	 * @param voteRequest
	 * @throws VotingException 
	 */
	VoteResponse vote(final VoteRequest voteRequest);

	/**
	 * Counts votes and send to queue
	 * 
	 * @param idAgenda
	 * @throws VotingException 
	 */
	void totalizeVotes(final String idAgenda);
	
}
