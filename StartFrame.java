import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Custom button class with oval-shaped buttons
class OvalButton extends JButton {
    public OvalButton(String text) {
        super(text);  // Call parent constructor with the button label
        setContentAreaFilled(false);  // Disable content area fill to customize button shape
    }

    // Method to paint the button's background
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {  // Change color when button is pressed
            g.setColor(Color.RED);
        } else {
            g.setColor(new Color(255, 130, 130));  // Default color when button is not pressed
        }
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);  // Draw a rounded rectangle for the button
        super.paintComponent(g);  // Call the parent method to handle text and other components
    }

    // Method to paint the button's border
    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK);  // Set border color to black
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);  // Draw the rounded rectangle border
    }
}

// Class for the start frame, allows user to select snake color and start the game
public class StartFrame extends JFrame {

    private final JComboBox<String> colorComboBox;  // Dropdown for selecting snake color

    // Constructor for StartFrame, initializes the UI
    public StartFrame() {
        this.setTitle("Snake Game");  // Set window title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close application when window is closed
        this.setSize(300, 300);  // Set window size
        this.setLayout(null);  // Use absolute positioning for components
        this.setLocationRelativeTo(null);  // Center window on screen
        this.getContentPane().setBackground(new Color(145, 180, 255));  // Set background color

        // Label for color selection
        JLabel colorLabel = new JLabel("Select Snake Color:");
        colorLabel.setBounds(90, 50, 150, 20);
        this.add(colorLabel);  // Add label to the frame

        // Dropdown with color options
        String[] colors = {"Blue", "Green", "Red"};
        colorComboBox = new JComboBox<>(colors);
        colorComboBox.setBounds(90, 80, 100, 20);
        this.add(colorComboBox);  // Add dropdown to the frame

        // Start button to begin the game
        OvalButton startButton = new OvalButton("Start");
        startButton.setBounds(90, 120, 100, 40);  // Set button position and size
        startButton.setForeground(Color.BLACK);  // Set text color
        startButton.addActionListener(new ActionListener() {  // Add event listener for button click
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the start frame
                String selectedColor = (String) colorComboBox.getSelectedItem();  // Get selected color
                Color snakeColor1 = Color.GREEN;  // Default snake colors
                Color snakeColor2 = new Color(40, 170, 0);
                assert selectedColor != null;
                switch (selectedColor) {
                    case "Blue" -> {
                        snakeColor1 = new Color(80, 100, 240);  // Blue snake colors

                        snakeColor2 = Color.BLUE;
                    }
                    case "Green" ->
                        // Green snake colors
                            snakeColor2 = new Color(40, 170, 0);
                    case "Red" -> {
                        snakeColor1 = new Color(255, 130, 130);  // Red snake colors

                        snakeColor2 = Color.RED;
                    }
                }
                new GameFrame(snakeColor1, snakeColor2);  // Start the game with selected colors
            }
        });

        this.add(startButton);  // Add button to the frame
        this.setVisible(true);  // Make the frame visible
    }

    // Main method to start the application
    public static void main(String[] args) {
        new StartFrame();  // Create and display the start frame
    }
}