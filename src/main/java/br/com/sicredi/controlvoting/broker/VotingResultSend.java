
package br.com.sicredi.controlvoting.broker;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.com.sicredi.controlvoting.beans.RabbitMqConfig;
import br.com.sicredi.controlvoting.dto.VotesTotalizedMessage;

@Service
public class VotingResultSend {
	private final Logger log;
	private final RabbitMqConfig rabbitMQConfig;
	private final RabbitTemplate rabbitTemplate;

	public VotingResultSend(Logger log, RabbitMqConfig rabbitMQConfig, RabbitTemplate rabbitTemplate) {
		this.log = log;
		this.rabbitMQConfig = rabbitMQConfig;
		this.rabbitTemplate = rabbitTemplate;

	}

	public void sendTotalization(VotesTotalizedMessage votesTotalizedMessage) {

		sendMessage(rabbitTemplate, rabbitMQConfig.getExchange(), votesTotalizedMessage,
				rabbitMQConfig.getRoutingKey());

	}

	private void sendMessage(RabbitTemplate template, String exchange, VotesTotalizedMessage data, String key) {
		log.info("Sending totalization: {}", data);
		template.convertAndSend(exchange, key, data);

	}
	
}
