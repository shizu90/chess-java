package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "N";
	}
	
	private boolean canMove(Position pos) {
		ChessPiece p = (ChessPiece)getBoard().piece(pos);
		return p == null || p.getColor() != this.getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] tmp = new boolean[getBoard().getRows()][getBoard().getCols()];
		
		Position p = new Position(0, 0);
		
		p.setValues(pos.getRow() - 1, pos.getCol() - 2);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}

		p.setValues(pos.getRow() - 2, pos.getCol() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}

		p.setValues(pos.getRow() - 2, pos.getCol() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}

		p.setValues(pos.getRow() - 1, pos.getCol() + 2);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}

		p.setValues(pos.getRow() + 1, pos.getCol() + 2);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}
		

		p.setValues(pos.getRow() + 2, pos.getCol() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}

		p.setValues(pos.getRow() + 2, pos.getCol() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}

		p.setValues(pos.getRow() + 1, pos.getCol() - 2);
		if(getBoard().positionExists(p) && canMove(p)) {
			tmp[p.getRow()][p.getCol()] = true;
		}
		
		return tmp;
	}

}
