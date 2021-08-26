package br.com.sicredi.controlvoting.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.sicredi.controlvoting.dto.AgendaResponse;
import br.com.sicredi.controlvoting.entity.Agenda;
import br.com.sicredi.controlvoting.repository.AgendaRepository;
import br.com.sicredi.controlvoting.repository.AgendaRepositoryCustom;

@SpringBootTest
class AgendaServiceTest {

	@Autowired
	AgendaService agendaService;
	
	@MockBean
	AgendaRepositoryCustom agendaRepositoryCustomMock; 
	
	@MockBean
	AgendaRepository agendaRepositoryMock;
	
	@BeforeEach
	void setUp() throws Exception {
		List<Agenda> agendasFalse = new ArrayList<>();
		agendasFalse.add(Agenda.builder()
				.idAgenda("1213")
				.active(false)
				.agendaName("Reforma UA Missões")
				.agendaDescription("UA insegura, necessário avaliação e ajustes")
				.votingStart(LocalDateTime.now().plus(10, ChronoUnit.MINUTES))
				.votationOpenFor(15)
				.created(Instant.now())
			.build());
		agendasFalse.add(Agenda.builder()
				.idAgenda("1214")
				.active(false)
				.agendaName("Reforma UA Batel")
				.agendaDescription("UA amarela, necessário ser verde")
				.votingStart(LocalDateTime.now().plus(2, ChronoUnit.SECONDS))
				.votationOpenFor(5)
				.created(Instant.now().minus(10, ChronoUnit.MINUTES))
			.build());

		Mockito.when(agendaRepositoryCustomMock.listAgendas(null)).thenReturn(agendasFalse);
		Mockito.when(agendaRepositoryCustomMock.listAgendas(false)).thenReturn(agendasFalse);
		
		List<Agenda> agendasTrue  = new ArrayList<>();
		agendasTrue.add(Agenda.builder()
				.idAgenda("1213")
				.active(false)
				.agendaName("Reforma UA Missões")
				.agendaDescription("UA insegura, necessário avaliação e ajustes")
				.votingStart(LocalDateTime.now().plus(10, ChronoUnit.MINUTES))
				.votationOpenFor(15)
				.created(Instant.now())
			.build());
		agendasTrue.add(Agenda.builder()
				.idAgenda("1214")
				.active(false)
				.agendaName("Reforma UA Batel")
				.agendaDescription("UA amarela, necessário ser verde")
				.votingStart(LocalDateTime.now().plus(2, ChronoUnit.SECONDS))
				.votationOpenFor(5)
				.created(Instant.now().minus(10, ChronoUnit.MINUTES))
			.build());

		agendasTrue.add(Agenda.builder()
				.idAgenda("1212")
				.active(false)
				.agendaName("Reforma UA Pioneiro")
				.agendaDescription("UA desatualizada, necessário revitalização")
				.votingStart(LocalDateTime.parse("2021-08-20T10:00:00"))
				.votationOpenFor(120)
				.created(Instant.now())
			.build());

		Mockito.when(agendaRepositoryCustomMock.listAgendas(true)).thenReturn(agendasTrue);
		
	}

	@Test
	final void testListFalseParam() {
		List<AgendaResponse> agendas = agendaService.list(false);
		
		Assertions.assertEquals(2, agendas.size());
		
	}

	@Test
	final void testListTrueParam() {
		List<AgendaResponse> agendas = agendaService.list(true);
		
		Assertions.assertEquals(3, agendas.size());
		
	}
	
}
