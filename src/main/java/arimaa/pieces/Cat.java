package src.main.java.arimaa.pieces;

import src.main.java.arimaa.game.*;

public class Cat extends Piece {
    public Cat(Position position, Color color) {
        super(position, 'C', color);
    }

    @Override
    public boolean canMove(Position newPosition, Board board) {
        // Implement movement rules specific to the Cat piece
        return true; // Placeholder logic
    }
}
