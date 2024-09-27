import java.awt.Color;
import javax.swing.*;

// Class definition for the game window frame
public class GameFrame extends JFrame {
    private GamePanel gamePanel;

    // Constructor for GameFrame, which initializes the game panel and sets up the frame
    GameFrame(Color snakeColor1, Color snakeColor2) {
        gamePanel = new GamePanel(snakeColor1, snakeColor2); // Initialize the game panel with snake colors
        this.add(gamePanel); // Add the game panel to the frame
        this.setTitle("Snake Game"); // Set the window title
        this.setResizable(false); // Prevent window resizing
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application when window is closed
        this.pack();  // Adjust window size to fit components
        this.setLocationRelativeTo(null);  // Center the window on the screen
        this.setVisible(true);  // Make the window visible
    }
}