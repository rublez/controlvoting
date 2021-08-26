package br.com.sicredi.controlvoting.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "heroku-app", url = "${consulta_associado.url}")
public interface CheckCpfClient {

	@GetMapping(value = "${consulta_associado.path}")
	String validateCpf(@PathVariable("cpf") final String cpf);
	
}
