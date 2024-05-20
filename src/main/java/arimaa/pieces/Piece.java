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

    public int getStrength() {
        return strength;
    }

    public void push(Piece other, Square newSquare, Board board) {
        if (isWeaker(other)) {
            if (other.canMove(newSquare, board)){
                Square currentSquare = other.square;
                board.movePiece(other.square, newSquare);
                board.movePiece(this.square, currentSquare);
            } else {
                System.out.println("Pushed piece must move to an adjacent square.");
            }
        } else {
            System.out.println("Cannot push a piece of equal or greater strength.");
        }
    }

    public void pull(Piece other, Square newSquare, Board board) {
        if (isWeaker(other)) {
            if (this.canMove(newSquare, board)){
                Square currentSquare = this.square;
                board.movePiece(this.square, newSquare);
                board.movePiece(other.square, currentSquare);
            } else {
                System.out.println("Your piece must move to an adjacent square when pulling other piece.");
            }
        } else {
            System.out.println("Cannot pull a piece of equal or greater strength.");
        }
    }

    private boolean isAdjacent(Piece other) {
        int dx = other.getSquare().getColumn() - this.square.getColumn();
        int dy = other.getSquare().getRow() - this.square.getRow();

        return (Math.abs(dx) == 1 && dy == 0) || (dx == 0 && Math.abs(dy) == 1);
    }

    public boolean isWeaker(Piece other) {
        return this.strength > other.strength;
    }

    public boolean isFrozen(Board board) {
        for (Square adjacentSquare : square.adjacentSquares(board)) {
            Piece adjacentPiece = board.getPieceAt(adjacentSquare);
            if (adjacentPiece != null && adjacentPiece.getColor() != color && adjacentPiece.getStrength() > strength && !hasFriendlyAdjacent(board)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasFriendlyAdjacent(Board board) {
        for (Square adjacentSquare : square.adjacentSquares(board)) {
            Piece adjacentPiece = board.getPieceAt(adjacentSquare);
            if (adjacentPiece != null && adjacentPiece.getColor() == color) {
                return true;
            }
        }
        return false;
    }

    public boolean canMove(Square newSquare, Board board) {
        if (this.getSquare().isAdjacent(newSquare) && board.getPieceAt(newSquare) == null) {
            if (type == 'R' && board.game.getCurrentPlayer().getColor() == Color.GOLD) {
                return newSquare.getRow() >= square.getRow();
            }
            if (type == 'r' && board.game.getCurrentPlayer().getColor() == Color.SILVER) {
                return newSquare.getRow() <= square.getRow();
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "type=" + type +
                ", position=" + square +
                '}';
    }
}
