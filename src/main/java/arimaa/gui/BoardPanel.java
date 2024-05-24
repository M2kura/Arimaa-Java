package src.main.java.arimaa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import src.main.java.arimaa.game.*;

/**
 * Represents the graphical user interface for the Arimaa game board.
 * The board panel displays the game board and handles user interactions.
 */
public class BoardPanel extends JPanel {
    private Game game;

    /**
     * Constructs a new board panel for the given game.
     *
     * @param game The game that this board panel is part of.
     */
    BoardPanel(Game game) {
        this.game = game;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX() / (getWidth() / 8);
                int y = 7 - e.getY() / (getHeight() / 8);
                handleSquareClick(x, y);
            }
        });
    }

    /**
     * Handles a click on a square on the board.
     * If the game is in the setup phase, the player can switch the positions of their pieces.
     * If the game is in the game phase, the player can select a piece to move or a piece to push/pull and the new square.
     *
     * @param x The x-coordinate of the clicked square.
     * @param y The y-coordinate of the clicked square.
     */
    private void handleSquareClick(int x, int y) {
        Square clickedSquare = game.getBoard().grid[x][y];
        Player currentPlayer = game.getCurrentPlayer();
        System.out.println("Square clicked: " + clickedSquare.getColumn() + clickedSquare.getRow());
        if (game.isSetupPhase()) {
            currentPlayer.addSquareForSwitch(clickedSquare, game.getBoard());
            repaint();
        } else {
            if (clickedSquare.hasPiece()) {
                Piece piece = clickedSquare.getPiece();
                if (piece.getColor() != currentPlayer.getColor()) {
                    currentPlayer.setPieceForPP(piece);
                } else {
                    currentPlayer.setPieceToMove(piece);
                }
            } else {
                currentPlayer.movePiece(clickedSquare, game.getBoard());
                currentPlayer.emptyPieceSelection();
                repaint();
            }
        }
    }

    /**
     * Returns the preferred size of the board panel.
     *
     * @return The preferred size of the board panel.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }

    /**
     * Paints the components of the board panel.
     * The board is divided into squares, each of which can contain a piece.
     * The pieces are represented by images.
     *
     * @param g The graphics context used to paint the components.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int squareSize = Math.min(getWidth(), getHeight()) / 8;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = game.getBoard().grid[i][7-j];
                g.setColor(square.getColor());
                g.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
                Piece piece = square.getPiece();
                if (piece != null) {
                    Image pieceImage = new ImageIcon("src/main/resources/images/pieces/" + Character.toUpperCase(piece.getType()) + "_" + piece.getColor() + ".png").getImage();
                    g.drawImage(pieceImage, i * squareSize, j * squareSize, squareSize, squareSize, this);
                }
            }
        }
    }
}
