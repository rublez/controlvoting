package br.com.sicredi.controlvoting.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.sicredi.controlvoting.dto.AgendaRequest;
import br.com.sicredi.controlvoting.dto.AgendaResponse;
import br.com.sicredi.controlvoting.dto.VoteRequest;
import br.com.sicredi.controlvoting.dto.VoteResponse;
import br.com.sicredi.controlvoting.service.AgendaService;
import br.com.sicredi.controlvoting.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Validated
@RestController
@RequestMapping("/votings/v1/")
public class VotingController {

	private final Logger log;
	private final AgendaService agendaService;
	private final VoteService voteService;

	public VotingController(Logger log, AgendaService agendaService, VoteService voteService) {
		super();
		this.log = log;
		this.agendaService = agendaService;
		this.voteService = voteService;

	}

	@Operation(summary = "Creates a new Agenda")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Agenda created sucessfully"),
			@ApiResponse(responseCode = "400", description = "Agenda could no be created") })
	@PostMapping(path = "/agenda", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(CREATED)
	public AgendaResponse create(@Validated @RequestBody final AgendaRequest request) {
		log.info("Creating new agenda: {}", request);

		return agendaService.create(request);

	}

	@Operation(summary = "List agendas. If parameter is false or ommited, lists only active and future agendas, if true, lists all persisted agendas")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List ok") })
	@GetMapping(path = "/agenda/{listAll}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(OK)
	public List<AgendaResponse> list(@PathVariable("listAll") final Boolean listAll) {
		log.debug("Listing agendas. Even finished ones: {}", listAll);

		return agendaService.list(listAll);

	}

	@Operation(summary = "Receives a new vote")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Voted sucessfully"),
			@ApiResponse(responseCode = "403", description = "Associate already voted"),
			@ApiResponse(responseCode = "404", description = "Associate not able to vote") })
	@PostMapping(path = "/vote", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(OK)
	public VoteResponse vote(@Validated @RequestBody final VoteRequest request) {
		log.info("Receiving new vote: {}", request);

		return voteService.vote(request);
	}

}
