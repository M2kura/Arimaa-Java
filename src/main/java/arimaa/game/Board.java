package src.main.java.arimaa.game;

import src.main.java.arimaa.pieces.Piece;

public class Board {
    private static final int SIZE = 8; // Arimaa has an 8x8 board
    private Piece[][] grid = new Piece[SIZE][SIZE];
    // Example: Trap positions (c3, c6, f3, f6)
    private final Position[] traps = {
            new Position("c3"),
            new Position("c6"),
            new Position("f3"),
            new Position("f6")
    };

    public Board() {

    }

    // Places a piece at the given position
    public void placePiece(Piece piece, Position position) {
        grid[position.getColumn() - 'a'][position.getRow() - 1] = piece;
    }

    // Retrieves the piece at a given position
    public Piece getPieceAt(Position position) {
        return grid[position.getColumn() - 'a'][position.getRow() - 1];
    }

    // Moves a piece from one position to another
    public boolean movePiece(Position from, Position to) {
        Piece piece = getPieceAt(from);
        if (piece != null && piece.canMove(to, this)) {
            grid[to.getColumn() - 'a'][to.getRow() - 1] = piece;
            grid[from.getColumn() - 'a'][from.getRow() - 1] = null;
            piece.move(to, this);
            return true;
        }
        return false;
    }

    public boolean isTrap(Position position) {
        for (Position trap : traps) {
            if (trap.equals(position)) {
                return true;
            }
        }
        return false;
    }

    public void printBoard() {
        System.out.println("  +-----------------+");

        for (int row = SIZE; row > 0; row--) {
            System.out.print(row + " | ");
            for (int col = 0; col < SIZE; col++) {
                Piece piece = grid[col][row - 1];
                Position currentPosition = new Position((char) ('a' + col), row);

                if (piece != null) {
                    System.out.print(piece.getType() + " ");
                } else if (isTrap(currentPosition)) {
                    System.out.print("x ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("|");
        }

        System.out.println("  +-----------------+");
        System.out.println("    a b c d e f g h");
    }
}
