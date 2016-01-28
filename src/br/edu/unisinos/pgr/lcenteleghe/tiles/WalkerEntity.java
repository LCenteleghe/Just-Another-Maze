package br.edu.unisinos.pgr.lcenteleghe.tiles;

import java.util.ArrayList;
import java.util.List;

import br.edu.unisinos.pgr.lcenteleghe.data.ActionType;
import br.edu.unisinos.pgr.lcenteleghe.entity.MapEntity;
import br.edu.unisinos.pgr.lcenteleghe.position.Direction;
import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;
import br.edu.unisinos.pgr.lcenteleghe.position.Point;

public abstract class WalkerEntity extends MapEntity {
	public WalkerEntity(MatrixPosition matrixPosition, TileMapView tileMapView,
			int layer) {
		super(matrixPosition, tileMapView, layer);
	}
	
	protected void step(Direction direction) {
		MatrixPosition newPosition = tileMapView.calculateMatrixWalk(
				currentMatrixPosition, direction);
		if (!tileMapView.hasColisionTileAt(newPosition.getRow(),
				newPosition.getCol())) {
			currentMatrixPosition = newPosition;

			Point tileScreenPoint = tileMapView.matrixPointToScreenPoint(
					currentMatrixPosition.getRow(),
					currentMatrixPosition.getCol(), true);

			boolean entitiesUpdated = false;
			if (direction.equals(Direction.WEST)
					|| direction.equals(Direction.NORTHWEST) || direction.equals(Direction.NORTH)) {
				updateMovementBindedMapEntities(newPosition);
				entitiesUpdated = true;
			}

			super.moveTo(tileScreenPoint.getX() - (this.getWidth() / (1.4f)),
					tileScreenPoint.getY() - this.getHeight() / 4, 10);

			if (!entitiesUpdated) {
				updateMovementBindedMapEntities(newPosition);
			}
		}
	}

	public void startWalking(final Direction direction, final long speed) {
		if (!isInMovement()) {
			walking = true;
			startWalkMovement(direction, speed);
			startWalkAnimation(direction, 100);
		}
	}

	
	private boolean walkDirectionChanged = false;;
	public void startWalking(final List<Direction> directions) {
		walkDirectionChanged = true;
		stopWalking();
		while (isInMovement()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		
		(new Thread() {
			public void run() {
				if (directions.isEmpty()) {
					return;
				}
				walkDirectionChanged = false;
				Direction lastDirection = directions.get(0);
				startWalkAnimation(lastDirection, 100);
				for (Direction direction : directions) {
					if(walkDirectionChanged){
						break;
					}
					if (!lastDirection.equals(direction)) {
						try {
							Thread.sleep(101);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						startWalkAnimation(direction, 100);
						lastDirection = direction;
					}

					step(direction);
				}
			}
		}).start();
	}

	private void startWalkMovement(final Direction direction, final long speed) {
		(new Thread() {
			public void run() {
				try {
					while (walking) {
						Thread.sleep(speed);
						step(direction);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void stopWalking() {
		walking = false;
	}

	private void startWalkAnimation(final Direction direction, final long speed) {
		(new Thread() {
			public void run() {
				try {
					while ((walking || isInMovement())) {
						Thread.sleep(speed);
						currentImage = animationSet.get(ActionType.WALK,
								direction).nextFrame(true);
						System.out.println("111 " + direction);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public List<Direction> buildPathTo(MatrixPosition matrixPosition) {
		return buildPathTo(matrixPosition.getRow(), matrixPosition.getCol());
	}

	public void walkTo(MatrixPosition mp) {
		walkTo(mp.getRow(), mp.getCol());
	}

	public void walkTo(int row, int col) {
		startWalking(buildPathTo(row, col));
	}

	public List<Direction> buildPathTo(int row, int col) {
		List<Direction> directions = new ArrayList<>();

		int w = col - (int) this.getCol();
		int h = row - (int) this.getRow();
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if (w < 0)
			dx1 = -1;
		else if (w > 0)
			dx1 = 1;
		if (h < 0)
			dy1 = -1;
		else if (h > 0)
			dy1 = 1;
		if (w < 0)
			dx2 = -1;
		else if (w > 0)
			dx2 = 1;
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		if (!(longest > shortest)) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if (h < 0)
				dy2 = -1;
			else if (h > 0)
				dy2 = 1;
			dx2 = 0;
		}
		int numerator = longest >> 1;
		for (int i = 0; i < longest; i++) {
			numerator += shortest;
			if (!(numerator < longest)) {
				numerator -= longest;
				if (dx1 == 1 && dy1 == 1) {
					directions.add(Direction.SOUTH);
				}
				if (dx1 == 1 && dy1 == -1) {
					directions.add(Direction.EAST);
				}
				if (dx1 == -1 && dy1 == 1) {
					directions.add(Direction.WEST);
				}
				if (dx1 == -1 && dy1 == -1) {
					directions.add(Direction.NORTH);
				}
			} else {
				if (dx2 == 1 && dy2 == 0) {
					directions.add(Direction.SOUTHEAST);
				}
				if (dx2 == 0 && dy2 == 1) {
					directions.add(Direction.SOUTHWEST);
				}
				if (dx2 == -1 && dy2 == 0) {
					directions.add(Direction.NORTHWEST);
				}
				if (dx2 == 0 && dy2 == -1) {
					directions.add(Direction.NORTHEAST);
				}
			}
		}

		return directions;
	}
}
