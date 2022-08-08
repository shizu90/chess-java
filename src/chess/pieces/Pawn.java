package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	public Pawn(Board board, Color color) {
		super(board, color);
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
		}else {
			p.setValues(pos.getRow() + 1, pos.getCol());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				tmp[p.getRow()][p.getCol()] = true;
			}
			p.setValues(pos.getRow() + 2, pos.getCol());
			Position p2 = new Position(pos.getRow() - 1, pos.getCol());
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
		}
		
		return tmp;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
