import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

// Class for the main game logic, extends JPanel and implements ActionListener
public class GamePanel extends JPanel implements ActionListener {
    // Constants defining the game window size and cell size
    static final int WINDOW_HEIGHT = 600;
    static final int WINDOW_WIDTH = 600;
    static final int CELL_SIZE = 30;
    static final int NUMBER_OF_CELLS = (WINDOW_HEIGHT * WINDOW_WIDTH) / CELL_SIZE;

    // Arrays to store snake body coordinates
    final int[] x = new int[NUMBER_OF_CELLS];
    final int[] y = new int[NUMBER_OF_CELLS];

    // Snake attributes
    int snakeLength = 5;  // Initial snake length
    int eatenApples;  // Score based on apples eaten
    int appleX;  // X position of the apple
    int appleY;  // Y position of the apple
    String direction = "right";  // Snake's direction of movement
    boolean moving = false;  // Boolean flag for movement state
    Timer timer;  // Timer for controlling the game loop speed
    static final int DELAY = 120;  // Delay between game loop iterations
    Random random;  // Random object for apple and special berry placement

    // Snake colors
    public Color snakeColor1;
    public Color snakeColor2;

    // Special berry attributes
    boolean isSpecialBerry = false;
    int specialBerryX;  // X position of the special berry
    int specialBerryY;  // Y position of the special berry
    int specialBerryTimer = 0;  // Timer for the duration of the special berry effect

    // Constructor for GamePanel, initializes colors and game state
    public GamePanel(Color snakeColor1, Color snakeColor2){
        this.snakeColor1 = snakeColor1;
        this.snakeColor2 = snakeColor2;
        random = new Random();  // Initialize random object
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));  // Set panel size
        this.setBackground(Color.BLACK);  // Set background color
        this.setFocusable(true);  // Enable focus for key events
        this.addKeyListener(new MyKeyAdapter());  // Add custom key listener
        startGame();  // Start the game
    }

    // Method to start the game, initializes apple and starts the game loop
    public void startGame(){
        spawnApple();  // Spawn the first apple
        moving = true;  // Set movement to true
        timer = new Timer(DELAY, this);  // Set up the game loop timer
        timer.start();  // Start the timer
    }

    // Overriding paintComponent to handle custom drawing on the panel
    public void paintComponent(Graphics g){
        super.paintComponent(g);  // Call superclass method to clear the screen
        draw(g);  // Call custom draw method
    }

    // Method to draw the game elements
    public void draw(Graphics g) {
        if (moving) {  // If the game is still running
            // Draw grid lines
            for (int i = 0; i < WINDOW_HEIGHT / CELL_SIZE; i++) {
                g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, WINDOW_HEIGHT);
                g.drawLine(0, i * CELL_SIZE, WINDOW_WIDTH, i * CELL_SIZE);
            }

            // Draw the apple
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, CELL_SIZE, CELL_SIZE);

            // Draw the snake
            for (int i = 0; i < snakeLength; i++) {
                if (i == 0) {
                    g.setColor(snakeColor1);  // Head of the snake
                } else {
                    g.setColor(snakeColor2);  // Body of the snake
                }
                g.fillRect(x[i], y[i], CELL_SIZE, CELL_SIZE);  // Draw snake segment
            }

            // Draw the score
            g.setColor(Color.PINK);
            g.setFont(new Font("Calibri", Font.ITALIC, 35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + eatenApples, (WINDOW_WIDTH - metrics.stringWidth("Score: " + eatenApples)) / 2, g.getFont().getSize());

        } else {
            endOfGame(g);  // If the game is over, show the end screen
        }

        // Draw the special berry if active
        if (isSpecialBerry) {
            g.setColor(Color.MAGENTA);
            g.fillOval(specialBerryX, specialBerryY, CELL_SIZE, CELL_SIZE);
        }
    }

    // Method to spawn a regular apple, ensuring it doesn't spawn on the snake
    public void spawnApple() {
        boolean check = true;
        while (check) {
            appleX = random.nextInt(WINDOW_WIDTH / CELL_SIZE) * CELL_SIZE;
            appleY = random.nextInt(WINDOW_HEIGHT / CELL_SIZE) * CELL_SIZE;
            check = false;
            for (int i = 0; i < snakeLength; i++) {
                if (x[i] == appleX && y[i] == appleY) {
                    check = true;
                    break;
                }
            }
        }

        // Randomly spawn a special berry occasionally
        if (random.nextInt(5) == 0 && !isSpecialBerry) {
            check = true;
            while (check) {
                specialBerryX = random.nextInt(WINDOW_WIDTH / CELL_SIZE) * CELL_SIZE;
                specialBerryY = random.nextInt(WINDOW_HEIGHT / CELL_SIZE) * CELL_SIZE;
                isSpecialBerry = true;
                check = false;
                for (int i = 0; i < snakeLength; i++) {
                    if (x[i] == specialBerryX && y[i] == specialBerryY) {
                        check = true;
                        break;
                    } else if (specialBerryX == appleX && specialBerryY == appleY) {
                        check = true;
                    }
                }
            }
        }
    }

    // Method to check if the snake ate an apple
    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            snakeLength++;  // Increase snake length
            eatenApples++;  // Increase score
            spawnApple();  // Spawn a new apple
        }
    }

    // Method to check if the snake ate a special berry
    public void checkSpecialBerry() {
        if (isSpecialBerry && x[0] == specialBerryX && y[0] == specialBerryY) {
            snakeLength++;  // Increase snake length
            eatenApples++;  // Increase score
            isSpecialBerry = false;  // Remove the special berry
            specialBerryTimer = 150;  // Start the special effect timer
            timer.setDelay((int) (timer.getDelay() * 0.5));  // Increase snake speed
        }
        if (specialBerryTimer > 0) {
            specialBerryTimer--;  // Decrease special effect timer
        } else {
            timer.setDelay(DELAY);  // Reset speed when timer ends
        }
    }

    // Method to move the snake based on the current direction
    public void move() {
        // Move each segment to the position of the previous one
        for (int i = snakeLength; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        // Move the head in the current direction
        switch(direction) {
            case "up":
                y[0] = y[0] - CELL_SIZE;
                break;
            case "down":
                y[0] = y[0] + CELL_SIZE;
                break;
            case "left":
                x[0] = x[0] - CELL_SIZE;
                break;
            case "right":
                x[0] = x[0] + CELL_SIZE;
                break;
        }
    }

    // Method to check for collisions with the snake's body or the game borders
    public void checkCollision() {
        for (int i = snakeLength; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                moving = false;  // Stop the game if snake collides with itself
                break;
            }
        }
        if (x[0] < 0 || x[0] >= WINDOW_WIDTH || y[0] < 0 || y[0] >= WINDOW_HEIGHT) {
            moving = false;  // Stop the game if snake hits the border
        }
        if (!moving) {
            timer.stop();  // Stop the timer if the game is over
        }
    }

    // Method to display the end-of-game screen
    public void endOfGame(Graphics g) {
        if (isSpecialBerry) {
            isSpecialBerry = false;  // Remove the special berry
            specialBerryTimer = 0;  // Reset special berry timer
        }

        // Draw the score
        g.setColor(Color.BLUE);
        g.setFont(new Font("Calibri", Font.ITALIC, 35));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + eatenApples, (WINDOW_WIDTH - metrics1.stringWidth("Score: " + eatenApples)) / 2, WINDOW_HEIGHT / 2 + 45);

        // Draw the "Game over" text
        g.setColor(Color.BLUE);
        g.setFont(new Font("Calibri", Font.ITALIC, 85));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game over", (WINDOW_WIDTH - metrics2.stringWidth("Game over")) / 2, WINDOW_HEIGHT / 2);
    }

    // Method that is called every time the timer fires
    @Override
    public void actionPerformed(ActionEvent e) {
        if (moving) {
            move();  // Move the snake
            checkApple();  // Check if apple is eaten
            checkSpecialBerry();  // Check if special berry is eaten
            checkCollision();  // Check for collisions
        }
        repaint();  // Redraw the panel
    }

    // Custom key adapter for handling key presses to control the snake
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !direction.equals("right")) {
                direction = "left";  // Change direction to left
            } else if (key == KeyEvent.VK_RIGHT && !direction.equals("left")) {
                direction = "right";  // Change direction to right
            } else if (key == KeyEvent.VK_UP && !direction.equals("down")) {
                direction = "up";  // Change direction to up
            } else if (key == KeyEvent.VK_DOWN && !direction.equals("up")) {
                direction = "down";  // Change direction to down
            }
        }
    }
}