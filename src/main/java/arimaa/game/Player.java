package src.main.java.arimaa.game;

import src.main.java.arimaa.pieces.*;
import java.util.List;

public class Player {
    public Piece.Color color;
    public boolean submitSetup = false;
    private Square[] squaresForSwitch = new Square[2];
    private int squareIndex = 0;

    public Player(Piece.Color color, Board board) {
        this.color = color;
        board.defaultSetUp(color);
    }

    public Piece.Color getColor() {
        return color;
    }

    public Move getMove() {
        // Implement logic to get a move from the player
        return null;
    }

    public void addSquareForSwitch(Square square, Board board) {
        Piece piece = square.getPiece();
        if (piece != null && piece.getColor() == this.color) {
            squaresForSwitch[squareIndex] = square;
            squareIndex++;
            if (squareIndex == 2) {
                board.switchPieces(squaresForSwitch[0], squaresForSwitch[1]);
                squareIndex = 0;
                squaresForSwitch = new Square[2];
            }
        } else {
            System.out.println("The square does not contain a player's piece.");
        }
    }

    public boolean submittedSetup() {
        return submitSetup;
    }
}
