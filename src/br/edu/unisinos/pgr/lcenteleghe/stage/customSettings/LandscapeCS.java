package br.edu.unisinos.pgr.lcenteleghe.stage.customSettings;

public class LandscapeCS{
	private String imagePath;
	private float x;
	private float y;
	
	public LandscapeCS(String imagePath, float x, float y) {
		super();
		this.imagePath = imagePath;
		this.x = x;
		this.y = y;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	
	
}
