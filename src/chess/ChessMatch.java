package chess;
import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private Board board;
	
	public ChessMatch() {
		this.board = new Board(8, 8);
		init();
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] tmp = new ChessPiece[this.board.getRows()][this.board.getCols()];
		for(int i = 0; i < this.board.getRows(); i++) {
			for(int j = 0; j < this.board.getCols(); j++) {
				tmp[i][j] = (ChessPiece) this.board.piece(i, j);
			}
		}
		return tmp;
	}
	
	private void placeNewPiece(char col, int row, ChessPiece piece) {
		this.board.placePiece(piece, new ChessPosition(col, row).toPosition());
	}
	
	private void init() {
		placeNewPiece('b', 6, new Rook(this.board, Color.WHITE));
		placeNewPiece('a', 2, new King(this.board, Color.BLACK));
	}
}
