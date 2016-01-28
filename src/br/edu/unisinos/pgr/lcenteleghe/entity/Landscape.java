package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.stage.customSettings.LandscapeCS;

public class Landscape extends Entity{
	private Image image;
	
	public Landscape(String PTMImagePath) {
		super(0, 0);
		image = new Image(PTMImagePath);
	}

	public Landscape(String PTMImagePath, float x, float y) {
		super(x, y);
		image = new Image(PTMImagePath);
	}

	public Landscape(LandscapeCS landscapeCustomSettings) {
		this(landscapeCustomSettings.getImagePath(), landscapeCustomSettings.getX(), landscapeCustomSettings.getY());
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public float getWidth() {
		return image.getWidth();
	}

	@Override
	public float getHeight() {
		return image.getHeight();
	}

	
}
