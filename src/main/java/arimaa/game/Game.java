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
        this.board = new Board(this);
        this.players = new Player[2];
        this.moveHistory = new ArrayList<>();
        this.isGameOver = false;
        this.turnCount = 1;
        setupGame();
    }

    public void setupGame() {
        // Initialize both players (Gold and Silver) with pieces
        players[0] = new Player(Piece.Color.GOLD, board);
        players[1] = new Player(Piece.Color.SILVER, board);
        currentPlayerIndex = 0;
        turnCount = 1;
    }

    public void startGame() {
        while (setupPhase) {
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
            for (int i = 0; i < 2; i++) {
                checkWinConditions();
                if (!isGameOver){
                    Player currentPlayer = getCurrentPlayer();
                    System.out.println("It's " + currentPlayer.getColor() + "'s turn.");
                    makeMove(currentPlayer);
                    while (!currentPlayer.submitMove) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    currentPlayer.submitMove = false;
                    currentPlayer.currentTurnMoves = 0;
                    switchTurns();
                }
            }
            turnCount++;
        }
        System.out.println("Game over!");
    }

    public void makeMove(Player player) {
        while (!player.submitMove) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void switchTurns() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

    private void checkWinConditions() {
        // Implement the logic to check for winning conditions
        // Example: Rabbit reaches the opposite side, or the opponent is immobilized
        // Set `isGameOver` to true if a player wins

        for (int col = 0; col < 8; col++) {
            Piece piece = board.getPieceAt(board.grid[col][7]);
            if (piece != null && piece.getType() == 'R') {
                isGameOver = true;
                System.out.println("Gold player wins!");
                return;
            }
        }
        for (int col = 0; col < 8; col++) {
            Piece piece = board.getPieceAt(board.grid[col][0]);
            if (piece != null && piece.getType() == 'r') {
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

    public void submitPlayerMove() {
        if (getCurrentPlayer().currentTurnMoves > 0 && getCurrentPlayer().currentTurnMoves < 5) {
            getCurrentPlayer().submitMove = true;
            System.out.println("Player " + (currentPlayerIndex + 1) + " submitted their move.");
        } else {
            System.out.println("You are supposed to do at least 1 and at most 4 moves per turn");
        }
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public Board getBoard() {
        return board;
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

    public Player getPlayer (int index) {
        return players[index];
    }
}
