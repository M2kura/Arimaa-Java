package src.main.java.arimaa.gui;

import src.main.java.arimaa.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame{
    private Image backgroundImage;

    public HomePage() {
        setTitle("Arimaa Game");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

//        backgroundImage = new ImageIcon("src/main/resources/images/homepage_bg.jpeg").getImage();

        JButton startButton = new JButton("Start New Game");
        JButton loadButton = new JButton("Load Game");
        JButton exitButton = new JButton("Exit");
        JButton friendButton = new JButton("Play with a Friend");
        friendButton.setVisible(false);
        JButton botButton = new JButton("Play with Bot");
        botButton.setVisible(false);

        friendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game();
                GameGUI gameGUI = new GameGUI(game);
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

        setVisible(true);
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        // Draw the background image
//        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//    }
}
