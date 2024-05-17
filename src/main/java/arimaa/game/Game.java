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
    private boolean setupPhase = true;

    public Game() {
        this.board = new Board();
        this.players = new Player[2];
        this.moveHistory = new ArrayList<>();
        this.isGameOver = false;
        this.turnCount = 0;
        setupGame();
    }

    public Board getBoard() {
        return board;
    }

    public void setupGame() {
        // Initialize both players (Gold and Silver) with pieces
        players[0] = new Player(Piece.Color.GOLD, board);
        players[1] = new Player(Piece.Color.SILVER, board);
        currentPlayerIndex = 0;
    }

    public void startGame() {
        while (setupPhase){
            if (players[0].submittedSetup() && players[1].submittedSetup()) {
                setupPhase = false;
                System.out.println("Setup phase is over.");
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        // Game loop
        while (!isGameOver) {
            Player currentPlayer = getCurrentPlayer();
            System.out.println("It's " + currentPlayer.getColor() + "'s turn.");

            // Assume getMove() is a method in Player class that returns a Move object
            Move move = currentPlayer.getMove();

            // Attempt to make the move
            if (makeMove(move)) {
                System.out.println(currentPlayer.getColor() + " made a move.");
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        System.out.println("Game over!");
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

    public void switchTurns() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

    private void checkWinConditions() {
        // Implement the logic to check for winning conditions
        // Example: Rabbit reaches the opposite side, or the opponent is immobilized
        // Set `isGameOver` to true if a player wins

        // Check if a gold rabbit has reached the last row
        for (int col = 0; col < 8; col++) {
            Piece piece = board.getPieceAt(board.grid['a' + col][7]);
            if (piece instanceof Rabbit && piece.getColor() == Piece.Color.GOLD) {
                isGameOver = true;
                System.out.println("Gold player wins!");
                return;
            }
        }
        for (int col = 0; col < 8; col++) {
            Piece piece = board.getPieceAt(board.grid['a' + col][0]);
            if (piece instanceof Rabbit && piece.getColor() == Piece.Color.SILVER) {
                isGameOver = true;
                System.out.println("Silver player wins!");
                return;
            }
        }
    }

    public void submitPlayerSetup() {
        players[currentPlayerIndex].submitSetup = true;
        System.out.println("Player " + (currentPlayerIndex+1) + " submitted their pieces.");
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

    public boolean isSetupPhase() {
        return setupPhase;
    }
}
