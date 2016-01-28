package br.edu.unisinos.pgr.lcenteleghe.exception;

import br.edu.unisinos.pgr.lcenteleghe.data.ActionType;
import br.edu.unisinos.pgr.lcenteleghe.position.Direction;

public class DuplicatedAnimationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4561441856804589569L;

	public DuplicatedAnimationException(ActionType actionType,
			Direction direction) {
		super("The animation with the key <" + actionType + "," + direction + " cannot be duplicated.");
	}
	
}
