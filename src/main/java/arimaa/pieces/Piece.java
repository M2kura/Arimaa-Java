package src.main.java.arimaa.pieces;

import src.main.java.arimaa.game.*;

public class Piece {
    public enum Color {
        GOLD, SILVER
    }

    protected Square square;
    protected final char type;
    protected final Color color;
    protected final int strength;

    public Piece(Square square, char type, Color color, int strength) {
        this.square = square;
        this.color = color;
        this.strength = strength;
        if (color == Color.GOLD) {
            this.type = Character.toUpperCase(type);
        } else {
            this.type = Character.toLowerCase(type);
        }
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public Square getSquare() {
        return square;
    }

    public char getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public boolean push(Piece other, Square newSquare, Board board) {
        if (isAdjacent(other) && isWeaker(other) && board.getPieceAt(newSquare) == null && newSquare.isAdjacent(other.square)) {
            Square currentSquare = other.square;
            other.move(newSquare, board);
            this.move(currentSquare, board);
            return true;
        }
        return false;
    }

    public boolean pull(Piece other, Square newSquare, Board board) {
        if (isAdjacent(other) && isWeaker(other) && board.getPieceAt(newSquare) == null && newSquare.isAdjacent(this.square)) {
            Square currentSquare = this.square;
            this.move(newSquare, board);
            other.move(currentSquare, board);
            return true;
        }
        return false;
    }

    private boolean isAdjacent(Piece other) {
        int dx = other.getSquare().getColumn() - this.square.getColumn();
        int dy = other.getSquare().getRow() - this.square.getRow();

        return (Math.abs(dx) == 1 && dy == 0) || (dx == 0 && Math.abs(dy) == 1);
    }

    public boolean isWeaker(Piece other) {
        return this.strength > other.strength;
    }

    public boolean canMove(Square newSquare, Board board) {
        if (this.getSquare().isAdjacent(newSquare) && board.getPieceAt(newSquare) == null) {
            if (type == 'r' || type == 'R') {
                return (color != Color.GOLD || newSquare.getRow() >= square.getRow()) &&
                        (color != Color.SILVER || newSquare.getRow() <= square.getRow()); // Rabbits cannot move backwards
            }
            return true;
        }
        return false;
    }

    // Updates the position after a successful move
    public void move(Square newSquare, Board board) {
        this.square = newSquare;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "type=" + type +
                ", position=" + square +
                '}';
    }
}
