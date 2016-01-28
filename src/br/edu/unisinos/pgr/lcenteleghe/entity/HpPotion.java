package br.edu.unisinos.pgr.lcenteleghe.entity;

import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.graphics.animation.Animation;

public class HpPotion extends Item{
	private Image image =  new Animation(2, "HPMP.ptm").getFrame(1);

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
		System.out.println("Voc� utilizou um pode de Hp");
	}

}
