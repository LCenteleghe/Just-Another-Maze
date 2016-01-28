package br.edu.unisinos.pgr.lcenteleghe.state;

import br.edu.unisinos.pgr.lcenteleghe.dataservices.GameSettings;
import br.edu.unisinos.pgr.lcenteleghe.entity.Landscape;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.graphics.ViewPort;

public class VictoryGameState extends AbstractGameState{
	private static VictoryGameState instance;
	private ViewPort viewPort = new ViewPort(false);
	private Landscape landscape;
	
	
	private VictoryGameState() {
		super();
		landscape = new Landscape("victoryScreen.ptm");
		initViewPortAndLayers();
	}

	public static VictoryGameState getInstance(){
		if(instance == null){
			instance = new VictoryGameState();
		}
		return instance;
	}

	@Override
	public Image getImage() {
		return viewPort.getImage();
	}

	@Override
	public void handleKeyboardInput(int eventKey, char eventCharacter,
			boolean eventKeyState) {
		
	}

	@Override
	public void handleMouseInput(int x, int y, boolean eventButtonState) {
	}

	@Override
	public void stateUpdate() {
	}
	
	private void initViewPortAndLayers(){
		viewPort = new ViewPort(0, 1,40f,40f, GameSettings.getInstance().getScreenWidth(), GameSettings.getInstance().getScreenHeight(), landscape.getImage());
		viewPort.addEntity(landscape);
	}

	@Override
	public AbstractGameState getNextState() {
		return this;
	}

}
