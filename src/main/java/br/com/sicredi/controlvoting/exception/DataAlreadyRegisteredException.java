package br.com.sicredi.controlvoting.exception;

public class DataAlreadyRegisteredException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4013647994166713977L;

	public DataAlreadyRegisteredException(final String message) {
		super(message);
		
	}

}