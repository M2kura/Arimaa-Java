package src.main.java.arimaa.gui;

import javax.swing.*;
import src.main.java.arimaa.game.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame {
    private MainPage mainPage;
    private Game game;
    private BoardPanel boardPanel;
    private ControlPanel controlPanel;
    private Thread gameThread;

    public GameGUI(Game game, MainPage mainPage) {
        this.mainPage = mainPage;
        this.game = game;
        this.boardPanel = new BoardPanel(game);
        this.controlPanel = new ControlPanel(this, game, mainPage);
        setTitle("Arimaa Game");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        add(boardPanel);
        add(controlPanel);
        layout.putConstraint(SpringLayout.WEST, boardPanel, 100, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, controlPanel, 10, SpringLayout.SOUTH, boardPanel);
        setVisible(true);
    }

    public void startGame() {
        gameThread = new Thread(() -> game.startGame());
        gameThread.start();
    }

    public void stopGame() {
        game = null;
        gameThread = null;
    }

    public void disposeGUI() {
        this.dispose();
    }

    public void refreshBoard() {
        boardPanel.repaint();
    }
}
