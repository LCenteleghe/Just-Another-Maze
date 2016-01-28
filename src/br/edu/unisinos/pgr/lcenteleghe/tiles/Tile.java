package br.edu.unisinos.pgr.lcenteleghe.tiles;

import br.edu.unisinos.pgr.lcenteleghe.graphics.IRenderable;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.graphics.RGB;

public class Tile implements IRenderable{
	private Image image;
	private int id;
	
	public Tile(Image image, int id) {
		super();
		this.image = image;
		this.id = id;
	}

	@Override
	public Image getImage() {
		return image;
	}
	
	public Tile(int id, int width, int height) {
		super();
		this.image = new Image(width, height);
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public void setColor(RGB rgb){
		Image.fullPopulate(image, rgb);
	}
	
	
	
}
