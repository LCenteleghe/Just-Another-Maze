package br.edu.unisinos.pgr.lcenteleghe.tiles;

public class MapLayer {
	private int[][] map;

	public MapLayer(int[][] map) {
		super();
		this.map = map;
	}

	public int getTileId(int row, int col){
		if(row<0 || col < 0 || row >= map.length || col >= map[0].length){
			return 0;
		}
		return map[row][col];
	}
	
	public void setTileId(int row, int col, int id){
		if(row<0 || col < 0 || row >= map.length || col >= map[0].length){
			return;
		}
		map[row][col] = id;
	}
}
