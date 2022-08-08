package boardgame;

public class Board {
	private int rows;
	private int cols;
	private Piece[][] pieces;
	
	public Board(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.pieces = new Piece[this.rows][this.cols];
	}
	
	public int getRows() {
		return this.rows;
	}
	public int getCols() {
		return this.cols;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	
	public Piece piece(int row, int col) {
		return this.pieces[row][col];
	}
	public Piece piece(Position pos) {
		return this.pieces[pos.getRow()][pos.getCol()];
	}
	public void placePiece(Piece piece, Position pos) {
		this.pieces[pos.getRow()][pos.getCol()] = piece;
		piece.pos = pos;
	}
}
