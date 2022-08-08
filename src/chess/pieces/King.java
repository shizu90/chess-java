package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{
	
	public King(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "K";
	}
	
	private boolean canMove(Position pos) {
		ChessPiece p = (ChessPiece)getBoard().piece(pos);
		return p == null || p.getColor() != this.getColor();
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
		
		return tmp;
	}
}
