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
	
	private void init() {
		this.board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
		this.board.placePiece(new King(board, Color.BLACK), new Position(4, 2));
	}
}
