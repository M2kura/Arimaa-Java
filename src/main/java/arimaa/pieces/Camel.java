package src.main.java.arimaa.pieces;

import src.main.java.arimaa.game.*;

public class Camel extends Piece {
    public Camel(Position position, Color color) {
        super(position, 'M', color);
    }

    @Override
    public boolean canMove(Position newPosition, Board board) {
        // Implement movement rules specific to the Camel piece
        return true; // Placeholder logic
    }
}
