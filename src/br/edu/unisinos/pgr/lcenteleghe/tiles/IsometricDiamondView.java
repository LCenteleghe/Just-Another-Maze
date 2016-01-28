package br.edu.unisinos.pgr.lcenteleghe.tiles;

import br.edu.unisinos.pgr.lcenteleghe.entity.MapEntity;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;
import br.edu.unisinos.pgr.lcenteleghe.position.Direction;
import br.edu.unisinos.pgr.lcenteleghe.position.MatrixPosition;
import br.edu.unisinos.pgr.lcenteleghe.position.Point;

public class IsometricDiamondView extends TileMapView {

	public IsometricDiamondView(TileSet tileSet, TileMap tileMap) {
		super(tileSet, tileMap);
	}

//	public void buildImage() {
//		int tilesWidth = tileMap.getTilesWidth();
//		int tilesHeight = tileMap.getTilesHeight();
//		int halfTilesWidth = tileMap.getTilesWidth() / 2;
//		int halfTilesHeight = tileMap.getTilesHeight() / 2;
//
//		image = new Image(tileMap.getCols() * tilesWidth + halfTilesWidth,
//				tileMap.getRows() * tilesHeight + halfTilesHeight);
//
//		for (MapLayer mapLayer : tileMap.getMapLayers()) {
//			for (int y = tileMap.getRows()-1; y >= 0; y--) {
//				for (int x = 0; x < tileMap.getCols(); x++) {// TODO
//					Tile tile = tileSet.getTileById(mapLayer.getTileId(
//							(tileMap.getCols() - y) - 1, x));
//					image.plot(
//							tile.getImage(),
//							(x * tilesWidth / 2) + (y * tilesWidth / 2),
//							((y * tilesHeight / 2) - (x * tilesHeight / 2))
//									+ ((tileMap.getRows() / 2) * tilesHeight - halfTilesHeight));
//				}
//			}
//		}
//	}

	@Override
	public MatrixPosition screenPointToMatrixPoint(float x, float y, boolean autoBound) {
		MatrixPosition matrixPosition = null;
		int tilesWidth = tileMap.getTilesWidth();
		int tilesHeight = tileMap.getTilesHeight();

		int halRowsNum = tileMap.getRows()/2;
		int ny = (int)(y/tilesHeight + x/tilesWidth) - halRowsNum;
		ny = tileMap.getRows() - ny - 1;
		
		
		int halfColsNum = tileMap.getCols() / 2;
		int nx = (int) ( y / tilesHeight - x /tilesWidth) + halfColsNum;
		
		nx = tileMap.getCols() - nx - ((nx>halfColsNum)?1:0);
		
		matrixPosition = new  MatrixPosition(nx, ny);
//
//		float xOffset = x - ((tilesWidth * nx) + tilesWidth / 2);
//		float yOffset = y - (tilesHeight * ny);

//		if (tileMap.getTileBaseImage().hasColorAt(xOffset, yOffset) || true) {
//			matrixPosition = new  MatrixPosition(nx, ny);
//		} else {
//			if (xOffset < tilesWidth / 2 && yOffset < tilesHeight / 2) {
//				matrixPosition = new MatrixPosition(nx, ny - 1);
//			}
//			if (xOffset >= tilesWidth / 2 && yOffset < tilesHeight / 2) {
//				matrixPosition = new MatrixPosition(nx + 1, ny - 1);
//			}
//			if (xOffset >= tilesWidth / 2 && yOffset >= tilesHeight / 2) {
//				matrixPosition =  new MatrixPosition(nx + 1, ny + 1);
//			}
//			if (xOffset < tilesWidth / 2 && yOffset >= tilesHeight / 2) {
//				matrixPosition = new MatrixPosition(nx - 1, ny + 1);
//			}
//		}

		if(autoBound){
			applyAutoBound(matrixPosition);
		}
		
		return matrixPosition;
	}
	
	public MatrixPosition screenPointToMatrixPoint2(float x, float y, boolean autoBound) {
		MatrixPosition matrixPosition = null;
		int tilesWidth = tileMap.getTilesWidth();
		int tilesHeight = tileMap.getTilesHeight();

		int ny = (int) ((x /tilesWidth) + (y/tilesHeight))
				- ((tileMap.getRows() / 2));
		
		ny = tileMap.getRows() - ny;
		
		int nx = (int) (( y / tilesHeight) - (x /tilesWidth)) + ((tileMap.getCols() / 2));
		
		nx = tileMap.getCols() - nx;

		float xOffset = x - ((tilesWidth * nx) + tilesWidth / 2);
		float yOffset = y - (tilesHeight * ny);

		if (tileMap.getTileBaseImage().hasColorAt(xOffset, yOffset)) {
			matrixPosition = new  MatrixPosition(nx, ny);
		} else {
			if (xOffset < tilesWidth / 2 && yOffset < tilesHeight / 2) {
				matrixPosition = new MatrixPosition(nx, ny - 1);
			}
			if (xOffset >= tilesWidth / 2 && yOffset < tilesHeight / 2) {
				matrixPosition = new MatrixPosition(nx + 1, ny - 1);
			}
			if (xOffset >= tilesWidth / 2 && yOffset >= tilesHeight / 2) {
				matrixPosition =  new MatrixPosition(nx + 1, ny + 1);
			}
			if (xOffset < tilesWidth / 2 && yOffset >= tilesHeight / 2) {
				matrixPosition = new MatrixPosition(nx - 1, ny + 1);
			}
		}

		if(autoBound){
			applyAutoBound(matrixPosition);
		}
		
		return matrixPosition;
	}

	private void applyAutoBound(MatrixPosition matrixPosition) {
		if(matrixPosition.getCol() >= tileMap.getCols()){
			matrixPosition.setCol(tileMap.getCols()-1);
		}
		
		if(matrixPosition.getRow() >= tileMap.getRows()){
			matrixPosition.setRow(tileMap.getRows()-1);
		}
		
		if(matrixPosition.getCol() < 0){
			matrixPosition.setCol(0);
		}
		
		if(matrixPosition.getRow() < 0){
			matrixPosition.setRow(0);
		}
	}

	@Override
	public Point matrixPointToScreenPoint(int row, int col, boolean centralized) {
		int tilesWidth = tileMap.getTilesWidth();
		int tilesHeight = tileMap.getTilesHeight();
		col = tileMap.getCols() - col;
		row = tileMap.getRows() - row;

		return new Point(((((tilesWidth * (row - col)) / 2) + (tileMap.getRows() * tilesWidth)/ 2)-(centralized?0:(tilesWidth/2))),
				 		  ((tilesHeight * (row + col)) / 2) - tilesHeight/(centralized?2:1));
	}

	@Override
	public MatrixPosition calculateMatrixWalk(MatrixPosition basePosition,
			Direction direction) {
		if (direction.equals(Direction.NORTH)) {
			return new MatrixPosition(basePosition.getCol() - 1,
					basePosition.getRow() - 1);
		}

		if (direction.equals(Direction.SOUTH)) {
			return new MatrixPosition(basePosition.getCol() + 1,
					basePosition.getRow() + 1);
		}

		if (direction.equals(Direction.EAST)) {
			return new MatrixPosition(basePosition.getCol() + 1,
					basePosition.getRow() - 1);
		}

		if (direction.equals(Direction.WEST)) {
			return new MatrixPosition(basePosition.getCol() - 1,
					basePosition.getRow() + 1);
		}

		if (direction.equals(Direction.NORTHWEST)) {
			return new MatrixPosition(basePosition.getCol() - 1,
					basePosition.getRow());
		}

		if (direction.equals(Direction.NORTHEAST)) {
			return new MatrixPosition(basePosition.getCol(),
					basePosition.getRow() - 1);
		}

		if (direction.equals(Direction.SOUTHEAST)) {
			return new MatrixPosition(basePosition.getCol() + 1,
					basePosition.getRow());
		}

		if (direction.equals(Direction.SOUTHWEST)) {
			return new MatrixPosition(basePosition.getCol(),
					basePosition.getRow() + 1);
		}

		return null;
	}

	@Override
	public MatrixPosition screenPointToMatrixPoint(float x, float y) {
		return screenPointToMatrixPoint(x, y, false);
	}

}
