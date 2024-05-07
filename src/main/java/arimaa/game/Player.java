package src.main.java.arimaa.game;

import src.main.java.arimaa.pieces.*;
import java.util.List;

public class Player {
    private List<Piece> pieces;
    private Piece.Color color;

    public Player(Piece.Color color) {
        this.color = color;
        // Initialize the pieces based on color
    }

    public Piece.Color getColor() {
        return color;
    }

    // Methods to manage the player's pieces and moves
}
