package src.main.java.arimaa.gui;

import javax.swing.*;
import src.main.java.arimaa.game.Game;

/**
 * Represents the graphical user interface for the Arimaa game.
 * The game GUI includes a board panel for displaying the game board and a control panel for displaying game messages and handling user interactions.
 * The game GUI also handles the game loop, which includes starting and stopping the game.
 */
public class GameGUI extends JFrame {
    private MainPage mainPage;
    private Game game;
    private BoardPanel boardPanel;
    private ControlPanel controlPanel;
    private Thread gameThread;

    /**
     * Constructs a new game GUI for the given game and main page.
     *
     * @param game The game that this game GUI is part of.
     * @param mainPage The main page of the application.
     */
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

    /**
     * Starts the game loop.
     * The game loop runs in a separate thread and includes updating the game state and refreshing the game GUI.
     */
    public void startGame() {
        gameThread = new Thread(() -> game.startGame());
        gameThread.start();
    }

    /**
     * Stops the game loop.
     * This is done by setting the game and the game thread to null.
     */
    public void stopGame() {
        game = null;
        gameThread = null;
    }

    /**
     * Disposes the game GUI.
     * This is done by calling the dispose method of the JFrame superclass.
     */
    public void disposeGUI() {
        this.dispose();
    }

    /**
     * Refreshes the board panel.
     * This is done by calling the repaint method of the board panel.
     */
    public void refreshBoard() {
        boardPanel.repaint();
    }

    /**
     * Returns the board panel.
     *
     * @return The board panel.
     */
    public BoardPanel getBoardPanel() {
        return boardPanel;
    }
}
