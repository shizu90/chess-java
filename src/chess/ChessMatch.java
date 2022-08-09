package chess;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> catchedPieces = new ArrayList<>();
	public ChessMatch() {
		this.board = new Board(8, 8);
		this.turn = 1;
		this.currentPlayer = Color.WHITE;
		this.check = false;
		init();
	}
	
	public int getTurn() {
		return this.turn;
	}
	
	public Color getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public boolean getCheck() {
		return this.check;
	}
	
	public boolean getCheckMate() {
		return this.checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return this.enPassantVulnerable;
	}
	public ChessPiece getPromoted() {
		return this.promoted;
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
		
		if(testCheck(this.currentPlayer)) {
			undoMove(src, trgt, catchedPiece);
			throw new ChessException("Your can't put yourself in check");
		}
		
		ChessPiece movedPiece = (ChessPiece)this.board.piece(trgt);
		
		//#special move promotion
		this.promoted = null;
		if(movedPiece instanceof Pawn) {
			if((movedPiece.getColor() == Color.WHITE && trgt.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && trgt.getRow() == 7)) {
				this.promoted = (ChessPiece)this.board.piece(trgt);
				this.promoted = replacePromotedPiece("Q");
			}
		}
		
		check = testCheck(opponent(this.currentPlayer)) ? true : false;
		if(testCheckMate(opponent(this.currentPlayer))) {
			checkMate = true;
		}else {
			nextTurn();
		}
		
		//#special move en passant
		if(movedPiece instanceof Pawn && (trgt.getRow() == src.getRow() - 2 || trgt.getRow() == src.getRow() + 2)) {
			this.enPassantVulnerable = movedPiece;
		}else {
			this.enPassantVulnerable = null;
		}
		
		return (ChessPiece)catchedPiece;
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if(this.promoted == null) throw new IllegalStateException("There is no piece to be promoted");
		if(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) 
			throw new InvalidParameterException("Invalid type for promotion");
		Position pos = this.promoted.getChessPosition().toPosition();
		Piece p = this.board.removePiece(pos);
		this.piecesOnTheBoard.remove(p);
		ChessPiece newPiece = newPiece(type, this.promoted.getColor());
		this.board.placePiece(newPiece, pos);
		this.piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	
	private ChessPiece newPiece(String type, Color color) {
		if(type.equals("B")) return new Bishop(this.board, color);
		if(type.equals("N")) return new Knight(this.board, color);
		if(type.equals("Q")) return new Queen(this.board, color);
		if(type.equals("N")) return new Knight(this.board, color);
		return new Rook(this.board, color);
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)this.board.removePiece(source);
		p.increaseMoveCount();
		Piece catchedPiece = this.board.removePiece(target);
		this.board.placePiece(p, target);
		
		if(catchedPiece != null) {
			this.piecesOnTheBoard.remove(catchedPiece);
			this.catchedPieces.add(catchedPiece);
		}
		
		//#special move castling kingside rook
		if(p instanceof King && target.getCol() == source.getCol() + 2) {
			Position sourceR = new Position(source.getRow(), source.getCol() + 3);
			Position targetR = new Position(source.getRow(), source.getCol() + 1);
			ChessPiece rook = (ChessPiece)this.board.removePiece(sourceR);
			this.board.placePiece(rook, targetR);
			rook.increaseMoveCount();
		}
		
		//#special move castling queenside rook
		if(p instanceof King && target.getCol() == source.getCol() - 2) {
			Position sourceR = new Position(source.getRow(), source.getCol() - 4);
			Position targetR = new Position(source.getRow(), source.getCol() - 1);
			ChessPiece rook = (ChessPiece)this.board.removePiece(sourceR);
			this.board.placePiece(rook, targetR);
			rook.increaseMoveCount();
		}
		
		//#special move es passant
		if(p instanceof Pawn) {
			if(source.getCol() != target.getCol() && catchedPiece == null) {
				Position pawnPos;
				if(p.getColor() == Color.WHITE) {
					pawnPos = new Position(target.getRow() + 1, target.getCol());
				}else {
					pawnPos = new Position(target.getRow() - 1, target.getCol());
				}
				catchedPiece = this.board.removePiece(pawnPos);
				catchedPieces.add(catchedPiece);
				piecesOnTheBoard.remove(catchedPiece);
			}
		}
		
		return catchedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece catchedPiece) {
		ChessPiece p = (ChessPiece)this.board.removePiece(target);
		p.decreaseMoveCount();
		this.board.placePiece(p, source);
		
		if(catchedPiece != null) {
			this.board.placePiece(catchedPiece, target);
			this.catchedPieces.remove(catchedPiece);
			this.piecesOnTheBoard.add(catchedPiece);
		}
		
		//#special move castling kingside rook
		if(p instanceof King && target.getCol() == source.getCol() + 2) {
			Position sourceR = new Position(source.getRow(), source.getCol() + 3);
			Position targetR = new Position(source.getRow(), source.getCol() + 1);
			ChessPiece rook = (ChessPiece)this.board.removePiece(targetR);
			this.board.placePiece(rook, sourceR);
			rook.decreaseMoveCount();
		}
		
		//#special move castling queenside rook
		if(p instanceof King && target.getCol() == source.getCol() - 2) {
			Position sourceR = new Position(source.getRow(), source.getCol() - 4);
			Position targetR = new Position(source.getRow(), source.getCol() - 1);
			ChessPiece rook = (ChessPiece)this.board.removePiece(targetR);
			this.board.placePiece(rook, sourceR);
			rook.decreaseMoveCount();
		}
		
		//#special move es passant
		if(p instanceof Pawn) {
			if(source.getCol() != target.getCol() && catchedPiece == this.enPassantVulnerable) {
				ChessPiece pawn = (ChessPiece)this.board.removePiece(target);
				Position pawnPos;
				if(p.getColor() == Color.WHITE) {
					pawnPos = new Position(3, target.getCol());
				}else {
					pawnPos = new Position(4, target.getCol());
				}
				this.board.placePiece(pawn, pawnPos);
				catchedPiece = this.board.removePiece(pawnPos);
			}
		}
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
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE; 
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list) {
			if(p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for(Piece p : opponentPieces) {
			boolean[][] adversaryMoves = p.possibleMoves();
			if(adversaryMoves[kingPosition.getRow()][kingPosition.getCol()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if(!testCheck(color)) return false;
		List<Piece> list = this.piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list) {
			boolean[][] tmp = p.possibleMoves();
			for(int i = 0; i < this.board.getRows(); i++) {
				for(int j = 0; j < this.board.getCols(); j++) {
					if(tmp[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece catchedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, catchedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void placeNewPiece(char col, int row, ChessPiece piece) {
		this.board.placePiece(piece, new ChessPosition(col, row).toPosition());
		this.piecesOnTheBoard.add(piece);
	}
	
	private void init() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
		
		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}
