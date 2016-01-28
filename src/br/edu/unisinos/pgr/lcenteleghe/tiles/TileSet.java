package br.edu.unisinos.pgr.lcenteleghe.tiles;

import java.util.ArrayList;
import java.util.List;

import br.edu.unisinos.pgr.lcenteleghe.exception.InvalidTileIdException;
import br.edu.unisinos.pgr.lcenteleghe.graphics.Image;

public class TileSet {
	private List<PartialTileSet> tileSets;
	Tile blackTile = new Tile(new Image("blackTile.ptm"), -1);
	
	public TileSet() {
		super();
		this.tileSets = new ArrayList<PartialTileSet>();
	}

	public Tile getTileById(int id){
		if(id==0){
			return new Tile(id,64,64);
		}
		
		if(id == -1){
			return blackTile;
		}
		
		if(id < 0){
			
		}
		
		for (PartialTileSet tileSet : tileSets) {
			if(id >= tileSet.getFirstTileId() && id < tileSet.getLastTileId()){
				return tileSet.getTileById(id);
			}
		}
		
		throw new InvalidTileIdException();
	}
	
	public void add(PartialTileSet partialTileSet){
		tileSets.add(partialTileSet);
	}
}
