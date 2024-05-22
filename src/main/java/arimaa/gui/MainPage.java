package src.main.java.arimaa.gui;

import src.main.java.arimaa.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import src.main.java.arimaa.game.Bot;
import src.main.java.arimaa.game.Piece;

public class MainPage extends JFrame{
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
                Object[] options = {"Gold", "Silver"};
                int n = JOptionPane.showOptionDialog(MainPage.this,
                        "Do you want to start as Gold or Silver?",
                        "Choose your color",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
                Game game = new Game();
                GameGUI gameGUI = new GameGUI(game, MainPage.this);
                if (n == JOptionPane.YES_OPTION) {
                    game.players[1] = new Bot(game, Piece.Color.SILVER, gameGUI.getBoardPanel());
                } else if (n == JOptionPane.NO_OPTION) {
                    game.players[0] = new Bot(game, Piece.Color.GOLD, gameGUI.getBoardPanel());
                }
                gameGUI.startGame();
                gameGUI.setVisible(true);
                setVisible(false);
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
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("Save files")); // Set the default directory to "Save files"
                int result = fileChooser.showOpenDialog(MainPage.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    Game game = new Game();
                    GameGUI gameGUI = new GameGUI(game, MainPage.this);
                    game.loadGame(selectedFile);
                    if (selectedFile.getName().contains("Bot_G")) {
                        game.players[0] = new Bot(game, Piece.Color.GOLD, gameGUI.getBoardPanel());
                    } else if (selectedFile.getName().contains("Bot_S")) {
                        game.players[1] = new Bot(game, Piece.Color.SILVER, gameGUI.getBoardPanel());
                    }
                    gameGUI.startGame();
                    gameGUI.setVisible(true);
                    setVisible(false);
                }
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
