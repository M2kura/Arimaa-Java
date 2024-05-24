package src.main.java.arimaa.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a square on an Arimaa game board.
 * Each square has a column and a row, and can hold a piece.
 * Squares can also be traps, which have special rules.
 */
public class Square {
    private char column;
    private int row;
    private Piece piece;
    private boolean isTrap;
    private Color color;

    /**
     * Constructs a new square with the given column and row, and whether it is a trap.
     *
     * @param column The column of the square on the board.
     * @param row The row of the square on the board.
     * @param isTrap Whether the square is a trap.
     */
    public Square(char column, int row, boolean isTrap) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new IllegalArgumentException("Invalid board position: " + column + row);
        }
        this.column = column;
        this.row = row;
        this.isTrap = isTrap;
        if (isTrap) {
            this.color = new Color(200, 100, 100, 255);
        } else if ((column - 'a' + row) % 2 == 0) {
            this.color = new Color(48, 48, 48, 255);
        } else {
            this.color = new Color(207, 202, 202, 255);
        }
    }

    /**
     * Returns the column of the square on the board.
     *
     * @return The column of the square on the board.
     */
    public char getColumn() {
        return column;
    }

    /**
     * Returns the row of the square on the board.
     *
     * @return The row of the square on the board.
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the piece on the square.
     *
     * @return The piece on the square, or null if the square is empty.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Returns the color of the square.
     *
     * @return The color of the square.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the piece on the square.
     *
     * @param piece The piece to place on the square.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
        if (piece != null) {
            piece.setSquare(this);
        }
    }

    /**
     * Returns whether the square is a trap.
     *
     * @return true if the square is a trap, false otherwise.
     */
    public boolean isTrap() {
        return isTrap;
    }

    /**
     * Returns whether the square has a piece.
     *
     * @return true if the square has a piece, false otherwise.
     */
    public boolean hasPiece() {
        return piece != null;
    }

    /**
     * Returns whether the square is adjacent to another square.
     * Two squares are adjacent if they are next to each other horizontally or vertically.
     *
     * @param other The other square to check.
     * @return true if the square is adjacent to the other square, false otherwise.
     */
    public boolean isAdjacent(Square other) {
        int dx = other.getColumn() - this.column;
        int dy = other.getRow() - this.row;

        return (Math.abs(dx) == 1 && dy == 0) || (dx == 0 && Math.abs(dy) == 1);
    }

    /**
     * Returns a list of squares that are adjacent to this square.
     *
     * @param board The game board.
     * @return A list of squares that are adjacent to this square.
     */
    public List<Square> adjacentSquares(Board board) {
        List<Square> adjacentSquares = new ArrayList<>();
        if (row > 1) adjacentSquares.add(board.grid[column - 'a'][row - 2]);
        if (row < 8) adjacentSquares.add(board.grid[column - 'a'][row]);
        if (column > 'a') adjacentSquares.add(board.grid[column - 'b'][row - 1]);
        if (column < 'h') adjacentSquares.add(board.grid[column - 'a' + 1][row - 1]);
        return adjacentSquares;
    }

    /**
     * Returns a string representation of the square.
     *
     * @return A string representation of the square.
     */
    @Override
    public String toString() {
        return "" + column + row;
    }
}
