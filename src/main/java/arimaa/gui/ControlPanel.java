package src.main.java.arimaa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import src.main.java.arimaa.game.*;

/**
 * Represents the control panel in the Arimaa game graphical user interface.
 * The control panel displays game messages and handles user interactions such as submitting moves and exiting the game.
 */
public class ControlPanel extends JPanel{
    private JTextArea textArea;

    /**
     * Constructs a new control panel for the given game GUI, game, and main page.
     *
     * @param gameGUI The game GUI that this control panel is part of.
     * @param game The game that this control panel is controlling.
     * @param mainPage The main page of the application.
     */
    public ControlPanel(GameGUI gameGUI, Game game, MainPage mainPage) {
        this.textArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);

        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);


        JButton exitButton = getjButton(game, mainPage, gameGUI);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!game.isSetupPhase()) {
                    if (!(game.getCurrentPlayer().currentTurnMoves == 0) && game.players[(game.currentPlayerIndex + 1) % 2] instanceof  Bot){
                        System.out.println("Move has been taken back.");
                    }
                    game.undoLastMove();
                    gameGUI.refreshBoard();
                }
            }
        });
        
        JButton submitButton = getjButton(game);

        add(exitButton);
        add(backButton);
        add(submitButton);
        add(scrollPane);
    }

    /**
     * Handles the action of clicking the exit button.
     * The user is asked whether they want to save the game before exiting.
     * If the user chooses to save the game, the game is saved and the game GUI is disposed.
     * If the user chooses not to save the game, the game GUI is disposed without saving the game.
     * In either case, the main page is made visible again.
     *
     * @param game The game that this control panel is controlling.
     * @param mainPage The main page of the application.
     * @param gameGUI The game GUI that this control panel is part of.
     * @return The exit button.
     */
    private static JButton getjButton(Game game, MainPage mainPage, GameGUI gameGUI) {
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Do you want to save the game before exiting?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    game.saveGame();
                    if (gameGUI != null) {
                        gameGUI.stopGame();
                        gameGUI.disposeGUI();
                    }
                    mainPage.setVisible(true);
                } else if (response == JOptionPane.NO_OPTION) {
                    if (gameGUI != null) {
                        gameGUI.stopGame();
                        gameGUI.disposeGUI();
                    }
                    mainPage.setVisible(true);
                }
            }
        });
        return exitButton;
    }

    /**
     * Handles the action of clicking the submit button.
     * If the game is in the setup phase, the player's setup is submitted and the turn is switched to the other player.
     * If the game is in the game phase and the game is not over, the player's move is submitted.
     *
     * @param game The game that this control panel is controlling.
     * @return The submit button.
     */
    private static JButton getjButton(Game game) {
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Submit button clicked");
                if (game.isSetupPhase()) {
                    game.submitPlayerSetup();
                    game.switchTurns();
                } else {
                    if (game.isGameOver()) {
                        System.out.println("The game is already over!");
                    } else {
                        game.submitPlayerMove();
                    }
                }
            }
        });
        return submitButton;
    }

    /**
     * Represents a custom output stream that writes to a text area.
     * This is used to redirect the standard output to the text area in the control panel.
     */
    public static class CustomOutputStream extends OutputStream {
        private JTextArea textArea;

        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            textArea.append(String.valueOf((char)b));
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    /**
     * Returns the preferred size of the control panel.
     *
     * @return The preferred size of the control panel.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 200);
    }
}
