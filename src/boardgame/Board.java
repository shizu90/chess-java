package boardgame;

public class Board {
	private int rows;
	private int cols;
	private Piece[][] pieces;
	
	public Board(int rows, int cols) {
		if(rows < 1 || cols < 1) throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
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
	
	public Piece piece(int row, int col) {
		if(!positionExists(row, col)) throw new BoardException("Position not on the board");
		return this.pieces[row][col];
	}
	public Piece piece(Position pos) {
		if(!positionExists(pos)) throw new BoardException("Position not on the board");
		return this.pieces[pos.getRow()][pos.getCol()];
	}
	public void placePiece(Piece piece, Position pos) {
		if(thereIsAPiece(pos)) throw new BoardException("There is already a piece on that position " + pos);
		this.pieces[pos.getRow()][pos.getCol()] = piece;
		piece.pos = pos;
	}
	
	private boolean positionExists(int row, int col) {
		return row >= 0 && row < this.rows && col >= 0 && col < this.cols;
	}
	
	public boolean positionExists(Position pos) {
		return positionExists(pos.getRow(), pos.getCol());
	}
	
	public boolean thereIsAPiece(Position pos) {
		if(!positionExists(pos)) throw new BoardException("Position not on the board");
		return piece(pos) != null;
	}
}
