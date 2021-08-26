package br.com.sicredi.controlvoting.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sicredi.controlvoting.dto.AgendaRequest;
import br.com.sicredi.controlvoting.dto.AgendaResponse;
import br.com.sicredi.controlvoting.entity.Agenda;
import br.com.sicredi.controlvoting.repository.AgendaRepository;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.yml")
@Disabled
class VotingControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @Autowired
	AgendaRepository agendaRepository;

	@BeforeAll
	public void setup() {
		agendaRepository.save(Agenda.builder()
				.agendaName("Ampliação UA Pioneira")
				.agendaDescription("Necessidade de ampliação para melhora do atendimento. Orçado R$ 750.000,00")
				.votingStart(LocalDateTime.parse("2021-08-25T00:11:00.000"))
				.votationOpenFor(90)
				.build());

		agendaRepository.save(Agenda.builder()
				.idAgenda("1214")
				.active(false)
				.agendaName("Reforma UA Batel")
				.agendaDescription("UA amarela, necessário ser verde")
				.votingStart(LocalDateTime.now().plus(2, ChronoUnit.SECONDS))
				.votationOpenFor(5)
				.created(Instant.now().minus(10, ChronoUnit.MINUTES))
			.build());

	}

	@Test
	final void testCreate() throws Exception {
		AgendaRequest agendaRequest = AgendaRequest.builder()
				.agendaName("Reforma UA Missões")
				.agendaDescription("UA insegura, necessário avaliação e ajustes")
				.votingStart(LocalDateTime.now().plus(10, ChronoUnit.MINUTES))
				.votationOpenFor(15)
				.created(Instant.now())
			.build();

		String votingStart = asJsonString(agendaRequest);
		MvcResult mvcResult = mvc.perform(post("/votings/v1/agenda")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(votingStart)).andReturn();

		Assertions.assertEquals(status().isOk(), mvcResult.getResponse());
		AgendaResponse agendaResponse = getContent(mvcResult.getResponse().getContentAsString());
		agendaRepository.deleteById(agendaResponse.getIdAgenda());
		
	}

	@Test
	final void testList() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testVote() {
		fail("Not yet implemented"); // TODO
	}

	
	@AfterAll
	public void clean() {
		
		
	}
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);

		} catch (Exception e) {
			throw new RuntimeException(e);

		}

	}

	
	private AgendaResponse getContent(String json) throws JsonMappingException, JsonProcessingException {
		return new ObjectMapper().readValue(json, AgendaResponse.class);
		
	}
	
}
