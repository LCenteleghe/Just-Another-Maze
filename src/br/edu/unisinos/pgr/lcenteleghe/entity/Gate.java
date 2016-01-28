package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.data.ActionType;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.ActionsAnimationSet;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.Animation;
import br.edu.unisinos.pgr.lcenteleghe.position.Direction;
import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;
import br.edu.unisinos.pgr.lcenteleghe.tiles.TileMapView;

public class Gate extends MapEntity{
	public boolean opened;

	public Gate(MatrixPosition matrixPosition, TileMapView tileMapView,
			int layer) {
		super(matrixPosition, tileMapView, layer);
		
		currentImage = animationSet.get(ActionType.OPEN, Direction.SOUTHWEST).getFrame(0);
	}

	@Override
	public float getWidth() {
		return animationSet.get(ActionType.OPEN, Direction.SOUTHWEST).getFrame(0).getWidth();
	}

	@Override
	public float getHeight() {
		return animationSet.get(ActionType.OPEN, Direction.SOUTHWEST).getFrame(0).getHeight();
	}

	@Override
	public void initAnimationSet() {
		this.animationSet = new ActionsAnimationSet();
		Animation animations = new Animation(1,4,  "images/gate/gate.ptm");
		animationSet.add(ActionType.OPEN, Direction.SOUTHWEST, animations);
	}
	
	public void open(){
		new Thread() {
			  public void run() {
				  try {
					  for (int i = 0; i < 4; i++) {
							Thread.sleep(30);
							currentImage = animationSet.get(ActionType.OPEN, Direction.SOUTHWEST).getFrame(i);
					}
				} catch (InterruptedException e) {e.printStackTrace();}
			  }
		}.start();
	}

	@Override
	public float insideTileXAlign() {
		return -this.getWidth()/4;
	}

	@Override
	public float insideTileYAlign() {
		return -getWidth()/6;
	}
	
	public void interactesWith(MapEntity mapEntity){
		if(mapEntity instanceof Player){
			if(!opened){
				if(((Player)mapEntity).getInventory().hasKey(true)){
					this.open();
					this.opened = true;
					this.tileMapView.removeColisionTileAt(this.getRow(), this.getCol());
					this.tileMapView.removeColisionTileAt(this.getRow(), this.getCol()+1);
					this.tileMapView.removeColisionTileAt(this.getRow()-1, this.getCol()+1);
				}
			}
		}
	}
	
}
