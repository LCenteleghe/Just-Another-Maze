package br.edu.unisinos.pgr.lcenteleghe.state;


public abstract class AbstractGameState implements IGameState{
	public abstract AbstractGameState getNextState();
}
