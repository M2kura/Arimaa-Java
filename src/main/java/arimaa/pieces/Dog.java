package src.main.java.arimaa.pieces;

import src.main.java.arimaa.game.*;

public class Dog extends Piece{
    public Dog(Position position, Color color) {
        super(position, 'D', color);
    }

    @Override
    public boolean canMove(Position newPosition, Board board) {
        // Implement movement rules specific to the Dog piece
        return true; // Placeholder logic
    }
}
