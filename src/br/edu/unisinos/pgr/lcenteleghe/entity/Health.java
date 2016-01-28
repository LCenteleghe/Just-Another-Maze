package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;
import br.edu.unisinos.pgr.lcenteleghe.tiles.TileMapView;

public class Health extends Item {

	private int healthEfect;
	

	
	public int use(){
		return this.healthEfect;
	}



	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public void onPlayerUse(Player player) {
		// TODO Auto-generated method stub
		
	}
	
	
}
