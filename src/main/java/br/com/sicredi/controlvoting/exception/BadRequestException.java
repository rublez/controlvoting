package br.com.sicredi.controlvoting.exception;

public class BadRequestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1958028898953864619L;

	public BadRequestException(final String message) {
		super(message);
		
	}
	
}
