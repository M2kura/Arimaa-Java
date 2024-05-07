package src.main.java.arimaa.pieces;

import src.main.java.arimaa.game.*;

public class Rabbit extends Piece {
    public Rabbit(Position position, Color color) {
        super(position, 'R', color);
    }

    @Override
    public boolean canMove(Position newPosition, Board board) {
        // Implement movement rules specific to the Rabbit piece
        return true; // Placeholder logic
    }
}
