package src.main.java.arimaa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import src.main.java.arimaa.game.*;
import src.main.java.arimaa.pieces.*;

public class BoardPanel extends JPanel {
    private Game game;

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

    private void handleSquareClick(int x, int y) {
        Square clickedSquare = game.getBoard().grid[x][y];
        Player currentPlayer = game.getCurrentPlayer();
        System.out.println("Square clicked: " + clickedSquare.getColumn() + clickedSquare.getRow());
        if (game.isSetupPhase()) {
            currentPlayer.addSquareForSwitch(clickedSquare, game.getBoard());
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
                repaint();
            }
        }
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }

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
