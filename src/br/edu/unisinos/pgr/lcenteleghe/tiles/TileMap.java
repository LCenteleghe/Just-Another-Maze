package br.edu.unisinos.pgr.lcenteleghe.tiles;

import java.util.List;

import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;


public class TileMap {
	private List<MapLayer> renderLayers;
	private MapLayer collisionLayer;
	private int cols;
	private int rows;
	private int tilesWidth;
	private int tilesHeight;
	private Image tileBaseImage;

	public TileMap(List<MapLayer> renderLayer, MapLayer collisionLayer, int cols, int rows, int tilesWidth,
			int tilesHeight, Image tileBaseImage) {
		super();
		this.renderLayers = renderLayer;
		this.cols = cols;
		this.rows = rows;
		this.tilesWidth = tilesWidth;
		this.tilesHeight = tilesHeight;
		this.tileBaseImage = tileBaseImage;
		this.collisionLayer = collisionLayer;
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}
	
	public int getTileId(int row, int col, int layer){
		return  renderLayers.get(layer).getTileId(row, col);
	}
	
	public boolean hasColisionTileAt(int row, int col){
		if (row > rows-1 || row < 0 || col > cols-1 || col < 0)
			return true;
		
		return collisionLayer.getTileId(row, col)>0;
	}
	
	public void removeColisionTileAt(int row, int col){
		collisionLayer.setTileId(row, col, 0);
	}

	public int getTilesWidth() {
		return tilesWidth;
	}

	public int getTilesHeight() {
		return tilesHeight;
	}

	public Image getTileBaseImage() {
		return tileBaseImage;
	}

	public void setTileBaseImage(Image tileBaseImage) {
		this.tileBaseImage = tileBaseImage;
	}

	public List<MapLayer> getMapLayers() {
		return renderLayers;
	}

	public int getLayers(){
		return renderLayers.size();
	}
}
