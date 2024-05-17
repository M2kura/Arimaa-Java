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
    private JFrame parentFrame;
    private Game game;
    private JTextArea textArea;

    public ControlPanel(JFrame parentFrame, Game game) {
        this.parentFrame = parentFrame;
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
                MainPage mainPage = new MainPage();
                mainPage.setVisible(true);
                parentFrame.setVisible(false);
            }
        });

        JButton submitButton = new JButton("Submit pieces setup");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Submit button clicked");
                if (game.isSetupPhase()) {
                    game.submitPlayerSetup();
                    game.switchTurns();
                }
            }
        });

        add(exitButton);
        add(submitButton);
        add(scrollPane);
    }

    public class CustomOutputStream extends OutputStream {
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
