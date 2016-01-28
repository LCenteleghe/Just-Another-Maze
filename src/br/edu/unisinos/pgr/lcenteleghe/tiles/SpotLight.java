package br.edu.unisinos.pgr.lcenteleghe.tiles;

import br.edu.unisinos.pgr.lcenteleghe.entity.MapEntity;
import br.edu.unisinos.pgr.lcenteleghe.position.Direction;
import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;

public class SpotLight extends MapEntity {
	private int radious;

	public SpotLight(MatrixPosition matrixPosition, TileMapView walkOver,
			int radious, long fadeTime) {
		super(matrixPosition, walkOver, 0);

		if (radious < 1) {
			throw new IllegalArgumentException(
					"The spotlight radious can't be lower than 1.");
		}

		this.radious = radious;

		this.startFading(fadeTime);
	}

	public int getRadious() {
		return radious;
	}

	public void incrementRadious(int inc) {
		this.radious += inc;
	}

	public void descrementRadious(int dec) {
		this.radious -= dec;
	}

	@Override
	public void initAnimationSet() {
	}

	@Override
	public float getWidth() {
		return 0;
	}

	@Override
	public float getHeight() {
		return 0;
	}

	@Override
	public float insideTileXAlign() {
		return 0;
	}

	@Override
	public float insideTileYAlign() {
		return 0;
	}

	public void startFading(final long time) {
		(new Thread() {
			public void run() {
				try {
					while (true) {
						Thread.sleep(time);
						descrementRadious(1);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
