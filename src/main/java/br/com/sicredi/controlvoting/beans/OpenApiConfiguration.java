package br.com.sicredi.controlvoting.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {

	@Value("${openApi.name}")
	private String appName;

	@Value("${openApi.description}")
	private String description;

	@Value("${openApi.version}")
	private String version;

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title(appName).description(description).version(version));
	}
}
