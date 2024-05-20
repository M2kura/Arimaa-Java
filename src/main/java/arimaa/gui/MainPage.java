package src.main.java.arimaa.gui;

import src.main.java.arimaa.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame{
    private Image backgroundImage;

    public MainPage() {
        setTitle("Arimaa Game");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton startButton = new JButton("Start New Game");
        JButton loadButton = new JButton("Load Game");
        JButton exitButton = new JButton("Exit");
        JButton friendButton = new JButton("Play with a Friend");
        friendButton.setVisible(false);
        JButton botButton = new JButton("Play with Bot");
        botButton.setVisible(false);
        JButton backButton = new JButton("Back");
        backButton.setVisible(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setVisible(true);
                loadButton.setVisible(true);
                exitButton.setVisible(true);
                friendButton.setVisible(false);
                botButton.setVisible(false);
                backButton.setVisible(false);
            }
        });

        friendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game();
                GameGUI gameGUI = new GameGUI(game, MainPage.this);
                gameGUI.startGame();
                gameGUI.setVisible(true);
                setVisible(false);
            }
        });
        botButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Leave this empty for now
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setVisible(false);
                loadButton.setVisible(false);
                exitButton.setVisible(false);
                friendButton.setVisible(true);
                botButton.setVisible(true);
                backButton.setVisible(true);
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the logic to load a game
                System.out.println("Load Game button clicked");
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });

        add(startButton);
        add(loadButton);
        add(exitButton);
        add(friendButton);
        add(botButton);
        add(backButton);

        setVisible(true);
    }
}
