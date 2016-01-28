package br.edu.unisinos.pgr.lcenteleghe.position;

public class MatrixPosition {
	private int row;
	private int col;
	public MatrixPosition(int col, int row) {
		super();
		this.row = row;
		this.col = col;
	}
	
	
	
	public MatrixPosition() {
		super();
	}



	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	
	public MatrixPosition clone(){
		return new MatrixPosition(row, col);
	}
	@Override
	public String toString() {
		return "MatrixPosition [row=" + row + ", col=" + col + "]";
	}
}	
