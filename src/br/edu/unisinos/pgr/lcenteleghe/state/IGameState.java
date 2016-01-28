package br.edu.unisinos.pgr.lcenteleghe.state;

import br.edu.unisinos.pgr.lcenteleghe.graphics.IRenderable;
import br.edu.unisinos.pgr.lcenteleghe.input.IUserHandlable;

/**
 * Default behavior for Game State classes.
 * @author Luis Gustavo
 */
public interface IGameState extends IState, IRenderable, IUserHandlable, IStateController {}
