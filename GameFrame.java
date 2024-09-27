import java.awt.Color;

import javax.swing.*;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    GameFrame(Color snakeColor1, Color snakeColor2) {
        gamePanel = new GamePanel(snakeColor1, snakeColor2);
        this.add(gamePanel);
        this.setTitle("Snake Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack(); 
        this.setLocationRelativeTo(null); 
        this.setVisible(true);
    }
}

