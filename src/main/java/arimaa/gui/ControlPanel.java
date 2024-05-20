package src.main.java.arimaa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import src.main.java.arimaa.game.*;

public class ControlPanel extends JPanel{
    private MainPage mainPage;
    private GameGUI gameGUI;
    private Game game;
    private JTextArea textArea;

    public ControlPanel(GameGUI mageGUI, Game game, MainPage mainPage) {
        this.mainPage = mainPage;
        this.gameGUI = gameGUI;
        this.game = game;
        this.textArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);

        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);


        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameGUI != null) {
                    gameGUI.stopGame();
                    gameGUI.disposeGUI();
                }
                mainPage.setVisible(true);
            }
        });

        JButton submitButton = getjButton(game);

        add(exitButton);
        add(submitButton);
        add(scrollPane);
    }

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
                    if (game.getCurrentPlayer().currentTurnMoves > 0){
                        game.submitPlayerMove();
                    } else {
                        System.out.println("You must make at least one move.");
                    }
                }
            }
        });
        return submitButton;
    }

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

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 200);
    }
}
