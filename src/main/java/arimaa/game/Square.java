package src.main.java.arimaa.game;

import java.util.ArrayList;
import java.util.List;
import src.main.java.arimaa.pieces.Piece;

public class Square {
    private char column;
    private int row;
    private Piece piece;
    private boolean isTrap;

    public Square(char column, int row, boolean isTrap) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new IllegalArgumentException("Invalid board position: " + column + row);
        }
        this.column = column;
        this.row = row;
        this.isTrap = isTrap;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isTrap() {
        return isTrap;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public boolean equals(Square other) {
        return this.column == other.column && this.row == other.row;
    }

    public boolean isAdjacent(Square other) {
        int dx = other.getColumn() - this.column;
        int dy = other.getRow() - this.row;

        return (Math.abs(dx) == 1 && dy == 0) || (dx == 0 && Math.abs(dy) == 1);
    }

    public List<Square> adjacentSquares(Board board) {
        List<Square> adjacentSquares = new ArrayList<>();
        if (row > 1) adjacentSquares.add(board.grid[column - 'a'][row - 2]);
        if (row < 8) adjacentSquares.add(board.grid[column - 'a'][row]);
        if (column > 'a') adjacentSquares.add(board.grid[column - 'b'][row - 1]);
        if (column < 'h') adjacentSquares.add(board.grid[column - 'a' + 1][row - 1]);
        return adjacentSquares;
    }

    @Override
    public String toString() {
        return "" + column + row;
    }
}
