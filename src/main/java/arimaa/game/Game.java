package src.main.java.arimaa.game;

import src.main.java.arimaa.gui.BoardPanel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Game {
    private final Board board;
    public Player[] players;
    public int currentPlayerIndex;
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
        players[0] = new Player(Piece.Color.GOLD);
        players[1] = new Player(Piece.Color.SILVER);
        currentPlayerIndex = 0;
        turnCount = 1;
    }

    public void startGame() {
        while (setupPhase) {
            for (int i = 0; i < 2; i++) {
                Player currentPlayer = players[i];
                if (currentPlayer instanceof Bot && !currentPlayer.submittedSetup()) {
                    ((Bot) currentPlayer).makeMove();
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (players[0].submittedSetup() && players[1].submittedSetup()) {
                    setupPhase = false;
                    turnCount++;
                    System.out.println("Setup phase is over.");
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        while (!isGameOver) {
            for (int i = 0; i < 2; i++) {
                Player currentPlayer = getCurrentPlayer();
                if (!moves.getLast().startsWith(turnCount + (currentPlayer.getColor() == Piece.Color.GOLD ? "g" : "s"))){
                    moves.add(turnCount + (currentPlayer.getColor() == Piece.Color.GOLD ? "g" : "s"));
                }
                checkWinConditions();
                if (!isGameOver){
                    System.out.println("It's " + currentPlayer.getColor() + "'s turn.");
                    if (currentPlayer instanceof Bot) {
                        ((Bot) currentPlayer).makeMove();
                    }
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
        if (currentPlayer.getColor() == Piece.Color.GOLD) {
            moves.add(turnCount + "g " + playerSetup.toString().trim());
            moves.add(turnCount + "s ");
        } else {
            addToMove(playerSetup.toString().trim());
        }
        System.out.println("Player " + (currentPlayerIndex+1) + " submitted their pieces.");
    }

    public void playerLost() {
        addToMove(" lost");
    }

    public void playerWins() {
        if (isGameOver) {
            return;
        }
        isGameOver = true;
        playerLost();
        Player previousPLayer = players[(currentPlayerIndex + 1) % 2];
        System.out.println(previousPLayer.getColor() + " player wins!");
    }

    public void submitPlayerMove() {
        if (getCurrentPlayer().currentTurnMoves > 0 && getCurrentPlayer().currentTurnMoves < 5) {
            getCurrentPlayer().submitMove = true;
            System.out.println("Player " + (currentPlayerIndex + 1) + " submitted their move.");
            if (getCurrentPlayer().getColor() == Piece.Color.SILVER) {
                turnCount ++;
            }
            if (!moves.isEmpty() && !setupPhase) {
                System.out.println(moves.getLast());
            }
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

    public void undoLastMove() {
        String lastMoveString = moves.removeLast();
        String[] individualMoves = lastMoveString.split(" ");
        if (isSetupPhase() || (turnCount == 2 && individualMoves[0].endsWith("g") && individualMoves.length == 1)) {
            return;
        }
        if (individualMoves.length == 1) {
            if (!(players[(currentPlayerIndex + 1) % 2] instanceof Bot)){
                moves.add(individualMoves[0] + " takeback");
                String previousMoveStart;
                if (getCurrentPlayer().getColor() == Piece.Color.GOLD) {
                    turnCount--;
                    previousMoveStart = turnCount + "s";
                } else {
                    previousMoveStart = turnCount + "g";
                }
                for (int i = moves.size() - 2; i >= 0; i--) {
                    String previousMoveString = moves.get(i);
                    if (previousMoveString.startsWith(previousMoveStart)) {
                        moves.add(previousMoveString);
                        break;
                    }
                }
                undoLastMove();
                getCurrentPlayer().submitMove = true;
            } else {
                moves.add(lastMoveString);
            }
        } else {
            for (int i = individualMoves.length - 1; i >= 1; i--) { // Start from 1 to skip the turn count and player char
                String move = individualMoves[i];
                char pieceType = move.charAt(0);
                char column = move.charAt(1);
                int row = Character.getNumericValue(move.charAt(2));
                char action = move.charAt(3);

                Square square = board.grid[column - 'a'][row - 1];
                Piece piece;

                if (action == 'x') {
                    piece = new Piece(square, pieceType, getCurrentPlayer().getColor());
                    board.placePiece(piece, square);
                } else {
                    Square destinationSquare = switch (action) {
                        case 'n' -> board.grid[column - 'a'][row];
                        case 's' -> board.grid[column - 'a'][row - 2];
                        case 'e' -> board.grid[column - 'a' + 1][row - 1];
                        default -> // 'w'
                                board.grid[column - 'a' - 1][row - 1];
                    };
                    piece = destinationSquare.getPiece();
                    destinationSquare.setPiece(null);
                    square.setPiece(piece);
                }
            }

            if (!moves.get(moves.size()-1).endsWith(" takeback")){
                moves.add(individualMoves[0]);
            }
            getCurrentPlayer().currentTurnMoves = 0;
        }
    }

    public void saveGame() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yy HH.mm");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = dtf.format(now);

        String directoryName = "Save files";
        String fileName = "Saved Game " + timestamp ;

        if (players[0] instanceof Bot) {
            fileName += " Bot_G";
        } else if (players[1] instanceof Bot) {
            fileName += " Bot_S";
        }
        fileName += ".txt";

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
            writer.write(board.printBoard());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            List<String> boardPrint = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (i < lines.size() - 11) {
                    moves.add(line);
                } else {
                    boardPrint.add(line);
                }
            }

            if (moves.size() >= 3) {
                setupPhase = false;
                players[0].submitSetup = true;
                players[1].submitSetup = true;
            } else if (moves.size() == 2) {
                players[0].submitSetup = true;
            }

            String lastMoveLine = moves.getLast();
            String[] lastMoveParts = lastMoveLine.split(" ");
            turnCount = Integer.parseInt(lastMoveParts[0].substring(0, lastMoveParts[0].length() - 1));
            currentPlayerIndex = lastMoveParts[0].endsWith("g") ? 0 : 1;

            getCurrentPlayer().currentTurnMoves = (int) Arrays.stream(lastMoveParts).skip(1).filter(move -> !move.endsWith("x")).count();

            for (int i = 0; i < 8; i++) {
                String line = lines.get(lines.size() - 10 + i);
                for (int j = 0; j < 8; j++) {
                    char pieceChar = line.charAt(4 + j * 2);
                    if (pieceChar != ' ' && pieceChar != 'x') {
                        Piece.Color color = Character.isUpperCase(pieceChar) ? Piece.Color.GOLD : Piece.Color.SILVER;
                        Piece piece = new Piece(null, pieceChar, color);
                        board.placePiece(piece, board.grid[j][7 - i]);
                    } else {
                        board.placePiece(null, board.grid[j][7 - i]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
