package br.edu.unisinos.pgr.lcenteleghe.entity;

import java.util.ArrayList;
import java.util.List;

import br.edu.unisinos.pgr.lcenteleghe.data.ActionType;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.ActionsAnimationSet;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.Animation;
import br.edu.unisinos.pgr.lcenteleghe.position.Direction;
import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;
import br.edu.unisinos.pgr.lcenteleghe.position.Point;
import br.edu.unisinos.pgr.lcenteleghe.tiles.TileMap;
import br.edu.unisinos.pgr.lcenteleghe.tiles.TileMapView;

public abstract class MapEntity extends Entity {
	protected ActionsAnimationSet animationSet;
	protected Direction currentDirection;
	protected TileMapView tileMapView;
	protected MatrixPosition currentMatrixPosition;
	protected int layer;
	protected Image currentImage;
	protected boolean walking;
	protected List<MapEntity> movementBindedEntities;

	public MapEntity(MatrixPosition matrixPosition, TileMapView tileMapView, int layer) {
		super(0, 0);
		initAnimationSet();
		this.movementBindedEntities = new ArrayList<MapEntity>();
		this.tileMapView = tileMapView;
		this.currentMatrixPosition = matrixPosition;
		this.layer = layer;
		
		Point tileScreenPoint = tileMapView.matrixPointToScreenPoint(
				matrixPosition.getRow(), matrixPosition.getCol(), true);
		
		super.y = tileScreenPoint.getY() + insideTileYAlign() ;
		super.x = tileScreenPoint.getX() + insideTileXAlign();
	}

	public void addMovementBindedEntity(MapEntity mapEntity){
		this.movementBindedEntities.add(mapEntity);
	}

	protected void updateMovementBindedMapEntities(MatrixPosition newPosition) {
		for (MapEntity mapEntity : movementBindedEntities) {
			mapEntity.setMatrixPosition(newPosition);
		}
	}
	
	@Override
	public Image getImage() {
		return currentImage;
	}

	@Override
	public abstract float getWidth();

	@Override
	public abstract float getHeight();
	
	public abstract void initAnimationSet();

	public int getLayer() {
		return layer;
	}

	public MatrixPosition getMatrixPosition() {
		return currentMatrixPosition;
	}
	
	public void setMatrixPosition(MatrixPosition matrixPosition){
		this.currentMatrixPosition = matrixPosition;
	}
	
	public int getRow(){
		return currentMatrixPosition.getRow();
	}
	
	public int getCol(){
		return currentMatrixPosition.getCol();
	}
	
	public void setCol(int col){
		this.currentMatrixPosition.setCol(col);
	}
	
	public void setRow(int row){
		this.currentMatrixPosition.setRow(row);
	}
	
	public void interactWithObjectsAround(){
		
		if(tileMapView.hasEntityAt(this.getRow(), this.getCol(), layer)){
			MapEntity mapEntity  = tileMapView.get(this.getRow(), this.getCol(), layer);
			this.interactesWith(mapEntity);
			mapEntity.interactesWith(this);

		} else		
			for (Direction direction : Direction.values()) {
				MatrixPosition matrixPosition = tileMapView.calculateMatrixWalk(currentMatrixPosition, direction);
				if(tileMapView.hasEntityAt(matrixPosition.getRow(), matrixPosition.getCol(), layer)){
					MapEntity mapEntity  = tileMapView.get(matrixPosition.getRow(), matrixPosition.getCol(), layer);
					this.interactesWith(mapEntity);
					mapEntity.interactesWith(this);
				}
			}
	}
	
	public void printPosition(){
		System.out.println(currentMatrixPosition);
	}

	
	public abstract float insideTileXAlign();
	public abstract float insideTileYAlign();
	
	public void interactesWith(MapEntity mapEntity){}
	
	public void setImage(Image image) {
		this.currentImage = image;
	}
}
