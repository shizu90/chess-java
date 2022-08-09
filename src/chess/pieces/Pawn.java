package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	private ChessMatch match;
	
	public Pawn(Board board, Color color, ChessMatch match) {
		super(board, color);
		this.match = match;
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] tmp = new boolean[getBoard().getRows()][getBoard().getCols()];
		Position p = new Position(0, 0);
		
		if(getColor() == Color.WHITE) {
			p.setValues(pos.getRow() - 1, pos.getCol());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				tmp[p.getRow()][p.getCol()] = true;
			}
			p.setValues(pos.getRow() - 2, pos.getCol());
			Position p2 = new Position(pos.getRow() - 1, pos.getCol());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && 
					getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				tmp[p.getRow()][p.getCol()] = true;
			}
			p.setValues(pos.getRow() - 1, pos.getCol() - 1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				tmp[p.getRow()][p.getCol()] = true;
			}
			p.setValues(pos.getRow() - 1, pos.getCol() + 1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				tmp[p.getRow()][p.getCol()] = true;
			}
			
			//#special move en passant white
			if(pos.getRow() == 3) {
				Position left = new Position(pos.getRow(), pos.getCol() - 1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == match.getEnPassantVulnerable()) {
					tmp[left.getRow() - 1][left.getCol()] = true;
				}
				Position right = new Position(pos.getRow(), pos.getCol() + 1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == match.getEnPassantVulnerable()) {
					tmp[right.getRow() - 1][right.getCol()] = true;
				}
			}
			
		}else {
			p.setValues(pos.getRow() + 1, pos.getCol());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				tmp[p.getRow()][p.getCol()] = true;
			}
			p.setValues(pos.getRow() + 2, pos.getCol());
			Position p2 = new Position(pos.getRow() + 1, pos.getCol());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && 
					getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				tmp[p.getRow()][p.getCol()] = true;
			}
			p.setValues(pos.getRow() + 1, pos.getCol() - 1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				tmp[p.getRow()][p.getCol()] = true;
			}
			p.setValues(pos.getRow() + 1, pos.getCol() + 1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				tmp[p.getRow()][p.getCol()] = true;
			}
			
			//#special move en passant black
			if(pos.getRow() == 4) {
				Position left = new Position(pos.getRow(), pos.getCol() - 1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == match.getEnPassantVulnerable()) {
					tmp[left.getRow() + 1][left.getCol()] = true;
				}
				Position right = new Position(pos.getRow(), pos.getCol() + 1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == match.getEnPassantVulnerable()) {
					tmp[right.getRow() + 1][right.getCol()] = true;
				}
			}
		}
		
		return tmp;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
