package src.main.java.arimaa.game;

import src.main.java.arimaa.gui.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a bot player in an Arimaa game.
 * The bot can make moves automatically during both the setup phase and the game phase.
 */
public class Bot extends Player {
    private Game game;
    private Random random;
    private List<Square> squaresWithPieces;
    private final BoardPanel boardPanel;

    /**
     * Constructs a new bot for the given game.
     *
     * @param game The game that this bot is part of.
     * @param color The color of the bot's pieces.
     * @param boardPanel The panel that displays the game board.
     */
    public Bot(Game game, Piece.Color color, BoardPanel boardPanel) {
        super(color);
        this.game = game;
        this.random = new Random();
        this.boardPanel = boardPanel;
    }

    /**
     * Makes a move for the bot.
     * The bot waits for 3 seconds before making a move to simulate thinking time.
     */
    public void makeMove() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (game.isSetupPhase()) {
            setupPhaseMove();
        } else {
            gamePhaseMove();
        }
        boardPanel.repaint();
    }

    /**
     * Makes a move for the bot during the setup phase.
     * The bot randomly switches the positions of its pieces.
     */
    private void setupPhaseMove() {
        getSquaresWithPieces();
        for (int i = 0; i < 15; i++) {
            int index1 = random.nextInt(squaresWithPieces.size());
            int index2 = random.nextInt(squaresWithPieces.size());
            addSquareForSwitch(squaresWithPieces.get(index1), game.getBoard());
            addSquareForSwitch(squaresWithPieces.get(index2), game.getBoard());
        }
        game.submitPlayerSetup();
        game.switchTurns();
    }

    /**
     * Gets the squares that contain the bot's pieces.
     */
    private void getSquaresWithPieces() {
        squaresWithPieces = new ArrayList<>();
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Square square = game.getBoard().grid[col][row];
                Piece piece = square.getPiece();
                if (piece != null && piece.getColor() == getColor()) {
                    squaresWithPieces.add(square);
                }
            }
        }
    }

    /**
     * Makes a move for the bot during the game phase.
     * The bot randomly moves its pieces to valid squares.
     */
    private void gamePhaseMove() {
        getSquaresWithPieces();
        int rounds = random.nextInt(4) + 1;
        for (int i = 0; i < rounds; i++) {
            Piece selectedPiece = null;
            List<Square> validMoves = new ArrayList<>();
            while (validMoves.isEmpty()) {
                Square selectedSquare = squaresWithPieces.get(random.nextInt(squaresWithPieces.size()));
                selectedPiece = selectedSquare.getPiece();
                if (selectedPiece.isFrozen(game.getBoard())) {
                    squaresWithPieces.remove(selectedSquare);
                    if (squaresWithPieces.isEmpty()) {
                        break;
                    }
                    continue;
                }
                Piece finalSelectedPiece = selectedPiece;
                validMoves = selectedSquare.adjacentSquares(game.getBoard()).stream().filter(square -> finalSelectedPiece.canMove(square, game.getBoard())).toList();
            }
            if (validMoves.isEmpty()) {
                break;
            }
            Square moveToSquare = validMoves.get(random.nextInt(validMoves.size()));
            setPieceToMove(selectedPiece);
            movePiece(moveToSquare, game.getBoard());
            getSquaresWithPieces();
        }
        game.submitPlayerMove();
    }

}
