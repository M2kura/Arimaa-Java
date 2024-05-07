package src.main.java.arimaa.pieces;

import src.main.java.arimaa.game.*;

public class Elephant extends Piece {
    public Elephant(Position position, Color color) {
        super(position, 'E', color);
    }

    @Override
    public boolean canMove(Position newPosition, Board board) {
        // Implement movement rules specific to the Elephant piece
        return true; // Placeholder logic
    }
}
