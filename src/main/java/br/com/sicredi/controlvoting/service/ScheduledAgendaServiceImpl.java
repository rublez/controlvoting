package br.com.sicredi.controlvoting.service;

import java.util.Date;

import org.slf4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import br.com.sicredi.controlvoting.entity.Agenda;
import br.com.sicredi.controlvoting.repository.AgendaRepository;
import br.com.sicredi.controlvoting.util.Helper;

@Service
public class ScheduledAgendaServiceImpl implements ScheduledAgendaService {
	private final Logger log;
	private final ThreadPoolTaskScheduler taskScheduler;
	private final AgendaRepository agendaRepository;
	private final VoteService voteService;

	public ScheduledAgendaServiceImpl(Logger log, ThreadPoolTaskScheduler taskScheduler,
			AgendaRepository agendaRepository, VoteService voteService) {
		this.log = log;
		this.taskScheduler = taskScheduler;
		this.agendaRepository = agendaRepository;
		this.voteService = voteService;

	}

	@Override
	public void schedule(String agendaName, Date scheduleTo, String idAgenda, Boolean active) {
		taskScheduler.schedule(new RunnableTask(agendaName, idAgenda, active), scheduleTo);

	}

	class RunnableTask implements Runnable {
		private String message;
		private Boolean active;
		private String idAgenda;

		public RunnableTask(String message, String idAgenda, Boolean active) {
			this.message = message;
			this.idAgenda = idAgenda;
			this.active = active;

		}

		@Override
		public void run() {
			final Agenda agendaPersisted = agendaRepository.findOneByIdAgenda(idAgenda);
			Agenda newAgenda = Helper.clone(agendaPersisted);
			newAgenda.setActive(active);
			
			agendaRepository.save(newAgenda);
			log.info("Task {} is {}", message, active ? "Active" : "Finished");
			
			if (Boolean.FALSE.equals(active)) {
				voteService.totalizeVotes(idAgenda);
				
			}

		}

	}

}
