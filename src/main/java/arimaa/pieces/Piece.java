package src.main.java.arimaa.pieces;

import src.main.java.arimaa.game.*;

public abstract class Piece {
    public enum Color {
        GOLD, SILVER
    }

    protected Position position;
    protected final char type;
    protected final Color color;

    public Piece(Position position, char type, Color color) {
        this.position = position;
        this.color = color;
        if (color == Color.GOLD) {
            this.type = Character.toUpperCase(type);
        } else {
            this.type = Character.toLowerCase(type);
        }
    }

    // Returns the current position of the piece
    public Position getPosition() {
        return position;
    }

    // Returns the type character, adjusting for color
    public char getType() {
        return type;
    }

    // Abstract method to validate whether the piece can move to the target position
    public abstract boolean canMove(Position newPosition, Board board);

    // Updates the position after a successful move
    public void move(Position newPosition, Board board) {
        this.position = newPosition;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "type=" + type +
                ", position=" + position +
                '}';
    }
}
