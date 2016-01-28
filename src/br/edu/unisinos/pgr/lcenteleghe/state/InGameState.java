package br.edu.unisinos.pgr.lcenteleghe.state;

import br.edu.unisinos.pgr.lcenteleghe.dataservices.GameStagesDataService;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.stage.GameStage;

public class InGameState extends AbstractGameState{
	private static InGameState instance;
	private AbstractGameState nextState;
	
	private GameStage gameStage;

	private InGameState() {
		gameStage = GameStagesDataService.getInstance().getLinkedGameStages();
		nextState = this;
	}
	
	public static InGameState getInstance(){
		if(instance == null){
			instance = new InGameState();
		}
		return instance;
	}

	@Override
	public Image getImage() {
		return gameStage.getImage();
	}

	@Override
	public AbstractGameState getNextState() {
		return nextState;
	}

	@Override
	public void handleKeyboardInput(int eventKey, char eventCharacter, boolean eventKeyState) {
		gameStage.handleKeyboardInput(eventKey, eventCharacter, eventKeyState);
	}

	@Override
	public void handleMouseInput(int x, int y, boolean eventButtonState) {
		gameStage.handleMouseInput(x, y, eventButtonState);
	}
	


	@Override
	public void stateUpdate() {
		if(gameStage.getNextState()==null){
			nextState = VictoryGameState.getInstance();
		} else {
			gameStage = gameStage.getNextState();
		}
	}
}
