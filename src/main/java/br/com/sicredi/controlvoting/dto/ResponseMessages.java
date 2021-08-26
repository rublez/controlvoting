package br.com.sicredi.controlvoting.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class ResponseMessages {

	private Set<String> messages;

	public ResponseMessages() {
		buildMessages();
	}

	public ResponseMessages(final Set<String> messages) {
		this.messages = messages;
	}

	public Set<String> getMessages() {
		return messages;
	}

	public void setMessages(final Set<String> messages) {
		this.messages = messages;
	}

	public void addMessage(final String message) {
		if (this.messages == null) {
			buildMessages();
		}

		this.messages.add(message);
	}

	private void buildMessages() {
		this.messages = new HashSet<>();
	}

}
