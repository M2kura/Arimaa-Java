package src.main.java.arimaa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import src.main.java.arimaa.game.Game;

public class BoardPanel extends JPanel {
    private Game game;

    BoardPanel(Game game) {
        this.game = game;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX() / (getWidth() / 8);
                int y = e.getY() / (getHeight() / 8);
                handleSquareClick(x, y);
            }
        });
    }

    private void handleSquareClick(int x, int y) {
        // Handle the square click
        // This is just a placeholder, you'll need to implement the actual logic
        System.out.println("Square clicked: " + x + ", " + y);
        // Update the game state and repaint the board
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        // Set the preferred size of the board panel
        return new Dimension(600, 600);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the board as a grid
        int squareSize = Math.min(getWidth(), getHeight()) / 8;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.DARK_GRAY);
                }
                g.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }

        // Draw the game state
        // This is just a placeholder, you'll need to implement the actual drawing
    }
}
