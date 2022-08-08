package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {

	public Queen(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "Q";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] tmp = new boolean[getBoard().getRows()][getBoard().getCols()];
		
		Position p = new Position(0, 0);
		
		//above
		p.setValues(pos.getRow() - 1, pos.getCol());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			tmp[p.getRow()][p.getCol()] = true;
			p.setRow(p.getRow() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) tmp[p.getRow()][p.getCol()] = true;
		
		//left
		p.setValues(pos.getRow(), pos.getCol() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			tmp[p.getRow()][p.getCol()] = true;
			p.setCol(p.getCol() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) tmp[p.getRow()][p.getCol()] = true;
		
		//right
		p.setValues(pos.getRow(), pos.getCol() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			tmp[p.getRow()][p.getCol()] = true;
			p.setCol(p.getCol() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) tmp[p.getRow()][p.getCol()] = true;
		
		//below
		p.setValues(pos.getRow() + 1, pos.getCol());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			tmp[p.getRow()][p.getCol()] = true;
			p.setRow(p.getRow() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) tmp[p.getRow()][p.getCol()] = true;
		
		//nw
		p.setValues(pos.getRow() - 1, pos.getCol() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			tmp[p.getRow()][p.getCol()] = true;
			p.setValues(p.getRow() - 1, p.getCol() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) tmp[p.getRow()][p.getCol()] = true;
		
		//ne
		p.setValues(pos.getRow() - 1, pos.getCol() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			tmp[p.getRow()][p.getCol()] = true;
			p.setValues(p.getRow() - 1, p.getCol() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) tmp[p.getRow()][p.getCol()] = true;
		
		//se
		p.setValues(pos.getRow() + 1, pos.getCol() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			tmp[p.getRow()][p.getCol()] = true;
			p.setValues(p.getRow() + 1, p.getCol() + 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) tmp[p.getRow()][p.getCol()] = true;
		
		//sw
		p.setValues(pos.getRow() + 1, pos.getCol() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			tmp[p.getRow()][p.getCol()] = true;
			p.setValues(p.getRow() + 1, p.getCol() - 1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) tmp[p.getRow()][p.getCol()] = true;
		
		return tmp;
	}
}
