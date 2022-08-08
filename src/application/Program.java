package application;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		
		while(true) {
			BoardBuilder.printBoard(chessMatch.getPieces());
			System.out.println();
			System.out.print("Source: ");
			ChessPosition source = BoardBuilder.readChessPosition(sc);
			System.out.println();
			System.out.print("Target: ");
			ChessPosition target = BoardBuilder.readChessPosition(sc);
			
			ChessPiece catchedPiece = chessMatch.performChessMove(source, target);
		}
	}

}
