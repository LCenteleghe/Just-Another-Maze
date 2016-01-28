package br.edu.unisinos.pgr.lcenteleghe.data;

import java.util.ArrayList;
import java.util.List;

import br.edu.unisinos.pgr.lcenteleghe.entity.MapEntity;

public class EntitiesMap {
	private MapEntity[/*row*/][/*col*/][/*layer*/] map;

	public EntitiesMap(int rows, int cols, int layer) {
		super();
		this.map = new MapEntity[rows][cols][layer];
	}
	
	
	public MapEntity get(int row, int col, int layer){
		return map[row][col][layer];
	}
	
	public void addEntity(MapEntity mapEntity){
		map[mapEntity.getRow()][mapEntity.getCol()][mapEntity.getLayer()] = mapEntity;
	}
	
	public boolean hasEntityAt(int row, int col, int layer){
		return  row >= 0 &&
				col >= 0 &&
				layer >= 0 &&
				row < map.length &&
				col < map[0].length &&
				layer < map[0][0].length &&
				get(row, col, layer)!=null;
	}

}
