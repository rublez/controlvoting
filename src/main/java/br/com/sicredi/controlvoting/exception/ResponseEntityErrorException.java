package br.com.sicredi.controlvoting.exception;

import org.springframework.http.ResponseEntity;

import br.com.sicredi.controlvoting.dto.ResponseMessages;

public class ResponseEntityErrorException extends RuntimeException {
	private ResponseEntity<ResponseMessages> errorResponse;

	public ResponseEntityErrorException(ResponseEntity<ResponseMessages> errorResponse) {
		this.errorResponse = errorResponse;
		
	}

	public ResponseEntity<ResponseMessages> getErrorResponse() {
		return errorResponse;
		
	}
	
}