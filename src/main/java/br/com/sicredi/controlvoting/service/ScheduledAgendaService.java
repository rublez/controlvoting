package br.com.sicredi.controlvoting.service;

import java.util.Date;

public interface ScheduledAgendaService {

	/**
	 * Schedule a new Agenda based on received parameters
	 * 
	 * @param agendaName
	 * @param scheduleTo
	 * @param idAgenda
	 * @param start
	 */
	void schedule(String agendaName, Date scheduleTo, String idAgenda, Boolean start);

}