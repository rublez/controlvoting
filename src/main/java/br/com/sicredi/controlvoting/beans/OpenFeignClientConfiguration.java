package br.com.sicredi.controlvoting.beans;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("br.com.sicredi.controlvoting.integration")
public class OpenFeignClientConfiguration {

}
