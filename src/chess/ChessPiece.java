package chess;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{
	private Color color;
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(pos);
	}
	
	protected boolean isThereOpponentPiece(Position pos) {
		ChessPiece p = (ChessPiece)getBoard().piece(pos);
		return p != null && p.getColor() != this.color; 
	}
}
