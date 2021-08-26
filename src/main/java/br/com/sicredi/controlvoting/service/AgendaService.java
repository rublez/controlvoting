package br.com.sicredi.controlvoting.service;

import java.util.List;

import br.com.sicredi.controlvoting.dto.AgendaRequest;
import br.com.sicredi.controlvoting.dto.AgendaResponse;

public interface AgendaService {

	/**
	 * Persists a new Agenda
	 * 
	 * @param agendaRequest
	 * @return
	 * @throws AgendaException 
	 */
	AgendaResponse create(final AgendaRequest agendaRequest);

	/**
	 * Lists active agendas. When all is <code>true</code> shows all existing agendas, past, active and future.  
	 * 
	 * @param listAll
	 * @return
	 */
	List<AgendaResponse> list(final Boolean listAll);

}
