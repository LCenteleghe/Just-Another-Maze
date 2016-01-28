package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.data.ActionType;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.ActionsAnimationSet;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.Animation;
import br.edu.unisinos.pgr.lcenteleghe.position.Direction;
import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;
import br.edu.unisinos.pgr.lcenteleghe.position.Point;
import br.edu.unisinos.pgr.lcenteleghe.tiles.SpotLight;
import br.edu.unisinos.pgr.lcenteleghe.tiles.TileMapView;
import br.edu.unisinos.pgr.lcenteleghe.tiles.WalkerEntity;


public class Player extends WalkerEntity{
	private SpotLight lantern;
	private boolean winner;
	private Inventory inventory;

	public Player(MatrixPosition matrixPosition, TileMapView walkOver, int layer, int lanterRadious) {
		super(matrixPosition, walkOver, layer);
		this.lantern = new SpotLight(matrixPosition, walkOver, lanterRadious, 60*1000);
		addMovementBindedEntity(lantern);

		inventory = new Inventory();
		currentImage = animationSet.get(ActionType.WALK, Direction.SOUTHEAST).getFrame(0);
		currentDirection = Direction.SOUTHEAST;
	}

	@Override
	public void initAnimationSet() {
		this.animationSet = new ActionsAnimationSet();
		for (Direction direction : Direction.values()) {
			animationSet.add(ActionType.WALK, direction, new Animation(5,2,  "images/player/walk/walk_" + direction.toString().toLowerCase() + ".ptm"));
		}
	}
	
	public SpotLight getLantern() {
		return lantern;
	}


	public void setLantern(SpotLight lantern) {
		this.lantern = lantern;
	}

	@Override
	public float getWidth() {
		return animationSet.get(ActionType.WALK, Direction.SOUTHEAST)
				.getFrame(0).getWidth();
	}

	@Override
	public float getHeight() {
		return animationSet.get(ActionType.WALK, Direction.SOUTHEAST)
				.getFrame(0).getWidth();
	}

	@Override
	public float insideTileXAlign() {
		return -this.getWidth()/1.4F;
	}

	@Override
	public float insideTileYAlign() {
		return -this.getHeight()/4;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setAsWinner() {
		this.winner = true;
	}

	public Inventory getInventory() {
		return inventory;
	}
	
	public void useItem(Item item){
		item.onPlayerUse(this);
	}
	
	public void usemItemFromInventory(int x, int y){
		Item item = inventory.getItem(x, y, true);
		if(item != null){
			useItem(item);
		}
	}
}
