package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;

public class LanternFuel extends Item{
	private Image image;

	public LanternFuel() {
		super();
		this.image = new Image("images/lantern/lanternFuel.ptm");
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void onPlayerUse(Player player) {
		player.getLantern().incrementRadious(2);
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
