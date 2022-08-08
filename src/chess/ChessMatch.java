package chess;
import boardgame.Board;
import boardgame.Piece;
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
	
	public boolean[][] possibleMoves(ChessPosition source) {
		Position pos = source.toPosition();
		validateSourcePosition(pos);
		return this.board.piece(pos).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition source, ChessPosition target) {
		Position src = source.toPosition();
		Position trgt = target.toPosition();	
		validateSourcePosition(src);
		validateTargetPosition(src, trgt);
		Piece catchedPiece = makeMove(src, trgt);
		return (ChessPiece)catchedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = this.board.removePiece(source);
		Piece catchedPiece = this.board.removePiece(target);
		this.board.placePiece(p, target);
		return catchedPiece;
	}
	
	private void validateSourcePosition(Position pos) {
		if(!this.board.thereIsAPiece(pos)) throw new ChessException("There is no piece in source position");
		if(!this.board.piece(pos).isThereAnyPossibleMove()) 
			throw new ChessException("There is no possible moves for the chosen piece");
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!this.board.piece(source).possibleMove(target)) throw new ChessException("The chosen piece can't move to target position");
	}
	
	private void placeNewPiece(char col, int row, ChessPiece piece) {
		this.board.placePiece(piece, new ChessPosition(col, row).toPosition());
	}
	
	private void init() {
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
