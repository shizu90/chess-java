package boardgame;

public class Piece {
	protected Position pos;
	protected Board board;
	
	public Piece(Board board) {
		this.board = board;
		this.pos = null;
	}
	
	public Board getBoard() {
		return this.board;
	}
}
