package src.main.java.arimaa.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Game {
    private final Board board;
    private final Player[] players;
    private int currentPlayerIndex;
    private boolean isGameOver;
    private int turnCount;
    private boolean setupPhase = true;
    private final List<String> moves;

    public Game() {
        this.board = new Board(this);
        this.players = new Player[2];
        this.isGameOver = false;
        this.turnCount = 1;
        this.moves = new ArrayList<>();
        setupGame();
    }

    public void setupGame() {
        players[0] = new Player(Piece.Color.GOLD, board);
        players[1] = new Player(Piece.Color.SILVER, board);
        currentPlayerIndex = 0;
        turnCount = 1;
    }

    public void startGame() {
        while (setupPhase) {
            if (players[0].submittedSetup() && players[1].submittedSetup()) {
                setupPhase = false;
                turnCount ++;
                System.out.println("Setup phase is over.");
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        while (!isGameOver) {
            for (int i = 0; i < 2; i++) {
                Player currentPlayer = getCurrentPlayer();
                moves.add(turnCount + (currentPlayer.getColor() == Piece.Color.GOLD ? "g" : "s"));
                checkWinConditions();
                if (!isGameOver){
                    System.out.println("It's " + currentPlayer.getColor() + "'s turn.");
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

    public void switchTurns() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }

    private void checkWinConditions() {
        for (int col = 0; col < 8; col++) {
            Piece piece = board.getPieceAt(board.grid[col][7]);
            if (piece != null && piece.getType() == 'R') {
                playerWins();
                return;
            }
        }
        for (int col = 0; col < 8; col++) {
            Piece piece = board.getPieceAt(board.grid[col][0]);
            if (piece != null && piece.getType() == 'r') {
                playerWins();
                return;
            }
        }
        int goldRabbits = 0;
        int silverRabbits = 0;
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Piece piece = board.getPieceAt(board.grid[col][row]);
                if (piece != null){
                    if (piece.getType() == 'R') {
                        goldRabbits++;
                    } else if (piece.getType() == 'r') {
                        silverRabbits++;
                    }
                }
            }
        }
        if (goldRabbits == 0) {
            playerWins();
            return;
        } else if (silverRabbits == 0) {
            playerWins();
            return;
        }
        boolean goldCanMove = false;
        boolean silverCanMove = false;
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Piece piece = board.getPieceAt(board.grid[col][row]);
                if (piece != null){
                    for (Square square : piece.getSquare().adjacentSquares(board)) {
                        if (piece.canMove(square, board)) {
                            if (piece.getColor() == Piece.Color.GOLD) {
                                goldCanMove = true;
                            } else {
                                silverCanMove = true;
                            }
                        }
                    }
                }
            }
        }
        if (!goldCanMove) {
            playerWins();
        } else if (!silverCanMove) {
            playerWins();
        }
    }

    public void submitPlayerSetup() {
        Player currentPlayer = players[currentPlayerIndex];
        currentPlayer.submitSetup = true;
        StringBuilder playerSetup = new StringBuilder();
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Piece piece = board.getPieceAt(board.grid[col][row]);
                if (piece != null && piece.getColor() == getCurrentPlayer().getColor()) {
                    playerSetup.append(piece.getType());
                    playerSetup.append(board.grid[col][row].toString());
                    playerSetup.append(" ");
                }
            }
        }
        moves.add(turnCount + (currentPlayer.getColor() == Piece.Color.GOLD ? "g " : "s ") + playerSetup.toString().trim());
        System.out.println("Player " + (currentPlayerIndex+1) + " submitted their pieces.");
    }

    public void playerLost() {
        addToMove(" lost");
    }

    public void playerWins() {
        isGameOver = true;
        playerLost();
        Player previousPLayer = players[(currentPlayerIndex + 1) % 2];
        System.out.println(previousPLayer.getColor() + " player wins!");
    }

    public void submitPlayerMove() {
        if (getCurrentPlayer().currentTurnMoves > 0 && getCurrentPlayer().currentTurnMoves < 5) {
            getCurrentPlayer().submitMove = true;
            System.out.println("Player " + (currentPlayerIndex + 1) + " submitted their move.");
        } else {
            System.out.println("You are supposed to do at least 1 and at most 4 moves per turn");
        }
    }

    public void movePiece(Piece piece, Square from, Square to) {
        char action;
        if (from.getColumn() == to.getColumn()) {
            if (from.getRow() < to.getRow()) {
                action = 'n';
            } else {
                action = 's';
            }
        } else {
            if (from.getColumn() < to.getColumn()) {
                action = 'e';
            } else {
                action = 'w';
            }
        }
        addToMove(" " + piece.getType() + from.toString() + action);
    }

    public void addToMove(String changes) {
        int lastString = moves.size() - 1;
        String lastMove = moves.get(lastString);
        lastMove += changes;
        moves.set(lastString, lastMove);
    }

    public void removePiece(Piece piece, Square from) {
        addToMove(" " + piece.getType() + from.toString() + "x");
    }

    public void saveGame() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yy HH.mm");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = dtf.format(now);

        String directoryName = "Save files";
        String fileName = "Saved Game " + timestamp + ".txt";

        try {
            Files.createDirectories(Paths.get(directoryName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(directoryName + "/" + fileName))) {
            for (String move : moves) {
                writer.write(move);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        //
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
