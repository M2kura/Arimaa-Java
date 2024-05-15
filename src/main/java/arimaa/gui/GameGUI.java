package src.main.java.arimaa.gui;

import javax.swing.*;
import src.main.java.arimaa.game.Game;

import java.awt.*;

public class GameGUI extends JFrame {
    private Game game;
    private BoardPanel boardPanel;
    private JPanel buttonAndPrintPanel;

    public GameGUI(Game game) {
        this.game = game;
        this.boardPanel = new BoardPanel(game);
        this.buttonAndPrintPanel = createButtonAndPrintPanel();

        // Set up the JFrame
        setTitle("Arimaa Game");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        add(boardPanel);
        add(buttonAndPrintPanel);
        setVisible(true);
    }

    private JPanel createButtonAndPrintPanel() {
        // Create a panel for the buttons and prints
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create the buttons and add them to the panel
        // ...

        return panel;
    }
}
