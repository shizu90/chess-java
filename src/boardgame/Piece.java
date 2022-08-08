package boardgame;

public abstract class Piece {
	protected Position pos;
	protected Board board;
	
	public Piece(Board board) {
		this.board = board;
		this.pos = null;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public abstract boolean[][] possibleMoves();
	public boolean possibleMove(Position pos) {
		return possibleMoves()[pos.getRow()][pos.getCol()];
	}
	public boolean isThereAnyPossibleMove() {
		boolean[][] tmp = possibleMoves();
		for(int i = 0; i < tmp.length; i++) {
			for(int j = 0; j < tmp.length; j++) {
				if(tmp[i][j]) return true;
			}
		}
		return false;
	}
}

