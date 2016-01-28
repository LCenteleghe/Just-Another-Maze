package br.edu.unisinos.pgr.lcenteleghe.input;

/**
 * Default behavior for classes that handle user input.
 * Any class that renders something on the screen must implement this interface.
 * @author Luis Gustavo
 */
public interface IUserHandlable {
	public void handleKeyboardInput(int eventKey , char eventCharacter, boolean eventKeyState);
	public void handleMouseInput(int x, int y, boolean eventButtonState);
}
