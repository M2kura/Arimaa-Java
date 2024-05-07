package src.main.java.arimaa.game;

import src.main.java.arimaa.pieces.Piece;

public class Board {
    private final int BOARD_SIZE = 8;
    private final Piece[][] grid;
    // Example: Trap positions (c3, c6, f3, f6)
    private final Position[] traps = {
            new Position("c3"),
            new Position("c6"),
            new Position("f3"),
            new Position("f6")
    };

    public Board() {
        grid = new Piece[BOARD_SIZE][BOARD_SIZE];
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
}
