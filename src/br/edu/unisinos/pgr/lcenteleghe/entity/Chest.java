package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.data.ActionType;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.ActionsAnimationSet;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.Animation;
import br.edu.unisinos.pgr.lcenteleghe.position.Direction;
import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;
import br.edu.unisinos.pgr.lcenteleghe.tiles.TileMapView;

public class Chest extends MapEntity{
	public Item itemInside;
	public boolean opened;
	public Direction direction;

	public Chest(MatrixPosition matrixPosition, TileMapView tileMapView,
			int layer, Item itemInside, Direction direction) {
		super(matrixPosition, tileMapView, layer);
		this.itemInside = itemInside;
		this.direction = direction;
		
		currentImage = animationSet.get(ActionType.OPEN, direction).getFrame(0);
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
		Animation animations = new Animation(4,4,  "images/chest/open/chest.ptm");
		animationSet.add(ActionType.OPEN, Direction.SOUTHWEST, animations.getSubAnimation(0, 4));
		animationSet.add(ActionType.OPEN, Direction.SOUTHEAST, animations.getSubAnimation(4, 4));
		animationSet.add(ActionType.OPEN, Direction.NORTHEAST, animations.getSubAnimation(8, 4));
		animationSet.add(ActionType.OPEN, Direction.NORTHWEST, animations.getSubAnimation(12, 4));
	}
	
	public void open(){
		new Thread() {
			  public void run() {
				  try {
					  while(animationSet.get(ActionType.OPEN, direction).hasNext()){
							Thread.sleep(30);
 							currentImage = animationSet.get(ActionType.OPEN, direction).nextFrame(false);
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
		if(!opened){
			this.open();
			if(mapEntity instanceof Player){
				((Player)mapEntity).getInventory().insertItem(itemInside);
			}
			this.opened = true;
		}
	}
	
}
