package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;

public class Key extends Item{
	private Image image;

	public Key() {
		super();
		this.image = new Image("images/key/oldKey.ptm");
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void onPlayerUse(Player player) {
		player.getInventory().insertItem(this);
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
