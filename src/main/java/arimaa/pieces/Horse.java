package src.main.java.arimaa.pieces;

import src.main.java.arimaa.game.*;

public class Horse extends Piece{
    public Horse(Position position, Color color) {
        super(position, 'H', color);
    }

    @Override
    public boolean canMove(Position newPosition, Board board) {
        // Implement movement rules specific to the Horse piece
        return true; // Placeholder logic
    }
}
