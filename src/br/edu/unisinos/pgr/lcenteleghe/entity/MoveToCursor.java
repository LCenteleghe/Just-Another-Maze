package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.data.ActionType;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.ActionsAnimationSet;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.Animation;
import br.edu.unisinos.pgr.lcenteleghe.position.Direction;
import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;
import br.edu.unisinos.pgr.lcenteleghe.position.Point;
import br.edu.unisinos.pgr.lcenteleghe.tiles.TileMapView;

public class MoveToCursor extends MapEntity{

	public MoveToCursor(MatrixPosition matrixPosition, TileMapView tileMapView,
			int layer) {
		super(matrixPosition, tileMapView, layer);
		
		currentImage = animationSet.get(ActionType.SHOW, Direction.SOUTH).getFrame(0);
		setInvisible();
	}

	@Override
	public float getWidth() {
		return animationSet.get(ActionType.SHOW, Direction.SOUTH).getFrame(0).getWidth();
	}

	@Override
	public float getHeight() {
		return animationSet.get(ActionType.SHOW, Direction.SOUTH).getFrame(0).getHeight();
	}

	@Override
	public void initAnimationSet() {
		this.animationSet = new ActionsAnimationSet();
		animationSet.add(ActionType.SHOW, Direction.SOUTH, new Animation(2,2,  "images/cursor/move.ptm"));
	}

	@Override
	public float insideTileXAlign() {
		return -this.getWidth()/2F;
	}

	@Override
	public float insideTileYAlign() {
		return  -this.getHeight()/2F;
	}
	
	public void setMatrixPosition(MatrixPosition matrixPosition){
		this.currentMatrixPosition = matrixPosition;
		
		Point tileScreenPoint = tileMapView.matrixPointToScreenPoint(
				matrixPosition.getRow(), matrixPosition.getCol(), true);
		
		super.y = tileScreenPoint.getY() + insideTileYAlign();
		super.x = tileScreenPoint.getX() + insideTileXAlign();
		startAnimation();
	}
	
	private void startAnimation(){
		setVisible();
		new Thread() {
			  public void run() {
				  try {
					  for (int i = 0; i < 4; i++) {
						  currentImage = animationSet.get(ActionType.SHOW, Direction.SOUTH).getFrame(i);
							Thread.sleep(80);
					  }
					  Thread.sleep(20);
					  setInvisible();
				} catch (InterruptedException e) {e.printStackTrace();}
			  }
		}.start();
	}

}
