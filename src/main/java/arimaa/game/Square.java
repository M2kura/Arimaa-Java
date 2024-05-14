package src.main.java.arimaa.game;

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

    @Override
    public String toString() {
        return "" + column + row;
    }
}
