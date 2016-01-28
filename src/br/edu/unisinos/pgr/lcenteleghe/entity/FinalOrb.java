package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.data.ActionType;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.ActionsAnimationSet;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.Animation;
import br.edu.unisinos.pgr.lcenteleghe.position.Direction;
import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;
import br.edu.unisinos.pgr.lcenteleghe.tiles.TileMapView;

public class FinalOrb extends MapEntity{

	private static final int pulseSpeed = 70;

	public FinalOrb(MatrixPosition matrixPosition, TileMapView tileMapView,
			int layer) {
		super(matrixPosition, tileMapView, layer);
		
		currentImage = animationSet.get(ActionType.PULSE, Direction.NORTH).getFrame(0);
		
		startPulsing();
	}

	@Override
	public float getWidth() {
		return animationSet.get(ActionType.PULSE, Direction.NORTH).getFrame(0).getWidth();
	}

	@Override
	public float getHeight() {
		return animationSet.get(ActionType.PULSE, Direction.NORTH).getFrame(0).getHeight();
	}

	
	@Override
	public void initAnimationSet() {
		this.animationSet = new ActionsAnimationSet();
		Animation animations = new Animation(3,2,  "images/orb/pulse/orb.ptm");
		animationSet.add(ActionType.PULSE, Direction.NORTH, animations.getSubAnimation(3, 3));
	}
	
	public void startPulsing(){
		new Thread() {
			  public void run() {
				  try {
					  while(true){
						  	for (int i = 0; i < 3; i++) {
								Thread.sleep(pulseSpeed);
						  		currentImage = animationSet.get(ActionType.PULSE, Direction.NORTH).getFrame(i);
							}
						  	
						  	for (int i = 2; i >= 0; i--) {
								Thread.sleep(pulseSpeed);
						  		currentImage = animationSet.get(ActionType.PULSE, Direction.NORTH).getFrame(i);
							}
					
							
					  }
				} catch (InterruptedException e) {e.printStackTrace();}
			  }
		}.start();
	}
	
	

	@Override
	public float insideTileXAlign() {
		return -getWidth()/3;
	}

	@Override
	public float insideTileYAlign() {
		return -getWidth()/6;
	}
	
	public void interactesWith(MapEntity mapEntity){
		if(mapEntity instanceof Player){
			((Player)mapEntity).setAsWinner();
		}
	}
	
}
