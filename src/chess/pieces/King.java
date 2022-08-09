package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{
	private ChessMatch match;
	
	public King(Board board, Color color, ChessMatch match) {
		super(board, color);
		this.match = match;
	}
	
	@Override
	public String toString() {
		return "K";
	}
	
	private boolean canMove(Position pos) {
		ChessPiece p = (ChessPiece)getBoard().piece(pos);
		return p == null || p.getColor() != this.getColor();
	}

	private boolean testRookCastling(Position pos) {
		ChessPiece p = (ChessPiece)getBoard().piece(pos);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] tmp = new boolean[getBoard().getRows()][getBoard().getCols()];
		
		Position p = new Position(0, 0);
		
		//above
		p.setValues(pos.getRow() - 1, pos.getCol());
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}
		//below
		p.setValues(pos.getRow() + 1, pos.getCol());
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}
		//left
		p.setValues(pos.getRow(), pos.getCol() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}
		//right
		p.setValues(pos.getRow(), pos.getCol() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}
		//nw
		p.setValues(pos.getRow() - 1, pos.getCol() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}
		
		//ne
		p.setValues(pos.getRow() - 1, pos.getCol() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}
		//sw
		p.setValues(pos.getRow() + 1, pos.getCol() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}
		//se
		p.setValues(pos.getRow() + 1, pos.getCol() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}
		
		//#special move castling
		if(getMoveCount() == 0 && !match.getCheck()) {
			//#special move castling kingside rook
			Position posR1 = new Position(pos.getRow(), pos.getCol() + 3);
			if(testRookCastling(posR1)) {
				Position p1 = new Position(pos.getRow(), pos.getCol() + 1);
				Position p2 = new Position(pos.getRow(), pos.getCol() + 2);
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					tmp[pos.getRow()][pos.getCol() + 2] = true;
				}
			}
			//#special move castling queenside rook
			Position posR2 = new Position(pos.getRow(), pos.getCol() - 4);
			if(testRookCastling(posR2)) {
				Position p1 = new Position(pos.getRow(), pos.getCol() - 1);
				Position p2 = new Position(pos.getRow(), pos.getCol() - 2);
				Position p3 = new Position(pos.getRow(), pos.getCol() - 3);
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					tmp[pos.getRow()][pos.getCol() - 2] = true;
				}
			}
		}
		
		return tmp;
	}
}
