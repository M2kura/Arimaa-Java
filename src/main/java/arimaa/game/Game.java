package src.main.java.arimaa.game;

import src.main.java.arimaa.pieces.*;
import src.main.java.arimaa.game.Board;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private Player[] players;
    private int currentPlayerIndex;
    private List<Move> moveHistory;
    private boolean isGameOver;
    private int turnCount;

    public Game() {
        this.board = new Board();
        this.players = new Player[2];
        this.moveHistory = new ArrayList<>();
        this.isGameOver = false;
        this.turnCount = 0;

        setupGame();
    }

    private void setupGame() {
        // Initialize both players (Gold and Silver) with pieces
        players[0] = new Player(Piece.Color.GOLD);
        players[1] = new Player(Piece.Color.SILVER);

        // Set up the initial board positions for all pieces based on Arimaa rules
        // Example:
        // board.placePiece(new Elephant(new Position('a', 1), Piece.Color.GOLD), new Position('a', 1));
        // Add more pieces...
    }

    public boolean makeMove(Move move) {
        if (isGameOver) {
            System.out.println("The game is already over!");
            return false;
        }

        // Attempt to move the piece on the board
        if (board.movePiece(move.getFrom(), move.getTo())) {
            moveHistory.add(move); // Record the move in history
            turnCount++;
            switchTurns(); // Switch to the other player's turn
            checkWinConditions();
            return true;
        }

        return false;
    }

    private void switchTurns() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2; // Toggle between 0 and 1
    }

    private void checkWinConditions() {
        // Implement the logic to check for winning conditions
        // Example: Rabbit reaches the opposite side, or the opponent is immobilized
        // Set `isGameOver` to true if a player wins
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public int getTurnCount() {
        return turnCount;
    }
}
