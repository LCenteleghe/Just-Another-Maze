package br.edu.unisinos.pgr.lcenteleghe.tiles;

import java.util.HashMap;
import java.util.Map;

import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;

public class PartialTileSet {
	private Map<Integer,Tile> tiles;
	private int firstTileId;
	private int lastTileId;
	
	public PartialTileSet(String imagePath, int firstTileId, int tileWidth, int tileHeight){
		Image image = new Image(imagePath);
		int cols = image.getWidth()/tileWidth;
		int rows = image.getHeight()/tileHeight;
		tiles = new HashMap<Integer, Tile>();
		
		this.firstTileId = firstTileId;
		this.lastTileId = firstTileId + cols*rows;
		
		for (Image tile : image.split(cols, rows)) {
			int currId = firstTileId++;
			tiles.put(currId, new Tile(tile, currId));
		}
	}
	
	public int getFirstTileId(){
		return firstTileId;
	}
	
	public int getLastTileId(){
		return lastTileId;
	}
	
	public Tile getTileById(int id){
		return tiles.get(id);
	}
}
