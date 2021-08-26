package br.com.sicredi.controlvoting.beans;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import br.com.sicredi.controlvoting.service.ScheduledAgendaServiceImpl;

@Configuration
@ComponentScan(basePackages = "br.com.sicredi.controlvoting.service", basePackageClasses = {
		ScheduledAgendaServiceImpl.class })
public class TasksSchedulerConfig implements SchedulingConfigurer {

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(6);
		threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");

		return threadPoolTaskScheduler;

	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(threadPoolTaskScheduler());

	}

	@Bean(destroyMethod = "shutdownNow")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(100);

	}

}
