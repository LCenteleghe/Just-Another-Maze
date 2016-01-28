package br.edu.unisinos.pgr.lcenteleghe.stage;

import br.edu.unisinos.pgr.lcenteleghe.graphics.IRenderable;
import br.edu.unisinos.pgr.lcenteleghe.input.IUserHandlable;
import br.edu.unisinos.pgr.lcenteleghe.state.IState;

/**
 * Default behavior for Game Stage classes.
 * @author Luis Gustavo
 */
public interface IGameStage extends IState, IRenderable, IUserHandlable {

	void setNextStage(IGameStage gameStage);}
