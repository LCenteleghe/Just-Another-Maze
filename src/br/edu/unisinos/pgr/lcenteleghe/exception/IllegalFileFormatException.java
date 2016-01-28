package br.edu.unisinos.pgr.lcenteleghe.exception;

public class IllegalFileFormatException extends RuntimeException {

	/**
	 *  This exception is thrown when an illegal format of file tries to be loaded. 
	 */
	private static final long serialVersionUID = 6242210283460854200L;

	public IllegalFileFormatException() {
		super();
	}

	public IllegalFileFormatException(String message) {
		super(message);
	}

	
	
}
