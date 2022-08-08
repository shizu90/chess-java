package chess;
import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private Board board;
	private int turn;
	private Color currentPlayer;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> catchedPieces = new ArrayList<>();
	public ChessMatch() {
		this.board = new Board(8, 8);
		this.turn = 1;
		this.currentPlayer = Color.WHITE;
		init();
	}
	
	public int getTurn() {
		return this.turn;
	}
	
	public Color getCurrentPlayer() {
		return this.currentPlayer;
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
		nextTurn();
		return (ChessPiece)catchedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = this.board.removePiece(source);
		Piece catchedPiece = this.board.removePiece(target);
		this.board.placePiece(p, target);
		
		if(catchedPiece != null) {
			this.piecesOnTheBoard.remove(catchedPiece);
			this.catchedPieces.add(catchedPiece);
		}
		return catchedPiece;
	}
	
	private void validateSourcePosition(Position pos) {
		if(!this.board.thereIsAPiece(pos)) throw new ChessException("There is no piece in source position");
		if(this.currentPlayer != ((ChessPiece)this.board.piece(pos)).getColor()) throw new ChessException("Chosen piece is not yours");
		if(!this.board.piece(pos).isThereAnyPossibleMove()) 
			throw new ChessException("There is no possible moves for the chosen piece");
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!this.board.piece(source).possibleMove(target)) throw new ChessException("The chosen piece can't move to target position");
	}
	
	public void nextTurn() {
		this.turn++;
		this.currentPlayer = (this.currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private void placeNewPiece(char col, int row, ChessPiece piece) {
		this.board.placePiece(piece, new ChessPosition(col, row).toPosition());
		this.piecesOnTheBoard.add(piece);
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
