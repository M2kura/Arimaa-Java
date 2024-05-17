package src.main.java.arimaa.gui;

import javax.swing.*;
import src.main.java.arimaa.game.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame {
    private Game game;
    private BoardPanel boardPanel;
    private ControlPanel controlPanel;

    public GameGUI(Game game) {
        this.game = game;
        this.boardPanel = new BoardPanel(game);
        this.controlPanel = new ControlPanel(this, game);
        setTitle("Arimaa Game");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        add(boardPanel);
        add(controlPanel);
        // Position boardPanel at 5 pixels from the left border of the GameGUI
        layout.putConstraint(SpringLayout.WEST, boardPanel, 100, SpringLayout.WEST, this.getContentPane());
        // Position controlPanel at 5 pixels from the bottom border of the boardPanel
        layout.putConstraint(SpringLayout.NORTH, controlPanel, 10, SpringLayout.SOUTH, boardPanel);
        setVisible(true);
        new Thread(() -> game.startGame()).start();
    }


}
