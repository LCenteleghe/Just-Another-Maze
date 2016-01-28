package br.edu.unisinos.pgr.lcenteleghe.stage;

import org.lwjgl.input.Keyboard;

import br.edu.unisinos.pgr.lcenteleghe.dataservices.GameSettings;
import br.edu.unisinos.pgr.lcenteleghe.dataservices.TileMapDataService;
import br.edu.unisinos.pgr.lcenteleghe.entity.Chest;
import br.edu.unisinos.pgr.lcenteleghe.entity.FinalOrb;
import br.edu.unisinos.pgr.lcenteleghe.entity.HpPotion;
import br.edu.unisinos.pgr.lcenteleghe.entity.Inventory;
import br.edu.unisinos.pgr.lcenteleghe.entity.LanternFuel;
import br.edu.unisinos.pgr.lcenteleghe.entity.MoveToCursor;
import br.edu.unisinos.pgr.lcenteleghe.entity.Player;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.graphics.ViewPort;
import br.edu.unisinos.pgr.lcenteleghe.input.KeyBoardDirectionsMap;
import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;
import br.edu.unisinos.pgr.lcenteleghe.stage.customSettings.StageCS;
import br.edu.unisinos.pgr.lcenteleghe.tiles.TileMapView;

/**
 * @author Luis Gustavo
 * This class represents a complete GameStage.
 */
public class GameStage implements IGameStage{
	private GameStage nextStage;
	@SuppressWarnings("unused")
	private int id;
	private TileMapView tileMapView;
	private ViewPort viewPort;
	private MoveToCursor moveToCursor;
	private Player player;
	
	public GameStage(StageCS gameStageCustomSettings) {
		super();
		tileMapView = TileMapDataService.getInstance().getTileMapViewFromXML();
		player = new Player(new MatrixPosition(189,188) , tileMapView, 1, 10);
		
		tileMapView.addSpotLight(player.getLantern());
		tileMapView.turnOnSpotLightMode();
		tileMapView.addPlayer(player);
		

		moveToCursor = new MoveToCursor(new MatrixPosition(194, 193), tileMapView, tileMapView.getMaxLayer());
		tileMapView.setMoveToCursor(moveToCursor);
		initViewPort();
		start();
	}
	
	public void start(){
	}
	
	private void initViewPort(){
		viewPort = new ViewPort((int)(player.getX()-GameSettings.getInstance().getScreenWidth()/2), 1,40f,40f, GameSettings.getInstance().getScreenWidth(), GameSettings.getInstance().getScreenHeight(), tileMapView.getImage());
		viewPort.addEntity(tileMapView);
		viewPort.setFollowed(player);
		viewPort.addEntity(player.getInventory());
	}

	@Override
	public GameStage getNextState() {
		if(player.isWinner()){
			return nextStage;
		} else {
			return this;
		}
	}
	
	public Image getImage(){
		return viewPort.getImage();
	}
	
	@Override
	public void handleKeyboardInput(int eventKey, char eventCharacter,
			boolean eventKeyState) {
		
		if(!player.getInventory().isVisible()){
			if(KeyBoardDirectionsMap.getInstance().hasDirectionForKey(eventKey) && eventKeyState)
				player.startWalking(KeyBoardDirectionsMap.getInstance().getDirection(eventKey), 5);
			else if(KeyBoardDirectionsMap.getInstance().hasDirectionForKey(eventKey) && !eventKeyState){
				player.stopWalking();
			}

			
			if(eventKeyState && eventKey == Keyboard.KEY_SPACE){
				player.interactWithObjectsAround();
			}
			
			if(eventKeyState && eventKey == Keyboard.KEY_2){
				player.printPosition();
				moveToCursor.printPosition();
			}
		}
		
		
		if(eventKeyState && eventKey == Keyboard.KEY_I){
			if(player.getInventory().isVisible()){
				player.getInventory().setInvisible();
			} else {
				player.stopWalking();
				player.getInventory().setVisible();
				player.getInventory().setX(viewPort.getX()+300);
				player.getInventory().setY(viewPort.getY()+100);
			}
		}
		
		
		
	}

	@Override
	public void handleMouseInput(int x, int y, boolean eventButtonState) {
		if(player.getInventory().isVisible() && eventButtonState){
			player.usemItemFromInventory(x, y);
		} else {
			if(eventButtonState){
				MatrixPosition mp = tileMapView.screenPointToMatrixPoint(viewPort.getXWithOffSet(x), viewPort.getYWithOffSet(y));
				tileMapView.showMoveToCursorAt(mp);
				player.walkTo(mp);
			}
		}
		
		System.out.println(tileMapView.screenPointToMatrixPoint(viewPort.getXWithOffSet(x), viewPort.getYWithOffSet(y)));
	}
	
	public void setNextStage(GameStage nextStage) {
		this.nextStage = nextStage;
	}
	
	public void onGameOver(){
	}


	@Override
	public void setNextStage(IGameStage gameStage) {
		// TODO Auto-generated method stub
		
	}
}