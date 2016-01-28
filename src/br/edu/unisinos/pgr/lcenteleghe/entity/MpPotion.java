package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.Animation;

public class MpPotion extends Item{
	private Image image =  new Animation(2, "HPMP.ptm").getFrame(0);

	@Override
	public float getWidth() {
		return image.getWidth();
	}

	@Override
	public float getHeight() {
		return image.getHeight();
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void onPlayerUse(Player player) {
		System.out.println("Você utilizou um pode de Hp");
	}

}
