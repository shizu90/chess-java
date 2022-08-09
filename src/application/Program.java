package application;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> catched = new ArrayList<>();
		
		
		while(!chessMatch.getCheckMate()) {
			try {				
				BoardBuilder.clearScreen();
				BoardBuilder.printMatch(chessMatch, catched);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = BoardBuilder.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				BoardBuilder.clearScreen();
				BoardBuilder.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = BoardBuilder.readChessPosition(sc);
				
				ChessPiece catchedPiece = chessMatch.performChessMove(source, target);
				
				if(catchedPiece != null) {
					catched.add(catchedPiece);
				}
				if(chessMatch.getPromoted() != null) {
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = sc.nextLine();
					while(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
						System.out.print("Enter piece for promotion (B/N/R/Q): ");
						type = sc.nextLine();
					}
					chessMatch.replacePromotedPiece(type.toUpperCase());
				}
				
			}catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		BoardBuilder.clearScreen();
		BoardBuilder.printMatch(chessMatch, catched);
	}

}
