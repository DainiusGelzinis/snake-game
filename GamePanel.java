import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int WINDOW_HEIGHT = 600;
    static final int WINDOW_WIDTH = 600;
    static final int CELL_SIZE = 30;
    static final int NUMBER_OF_CELLS = (WINDOW_HEIGHT * WINDOW_WIDTH) / CELL_SIZE;
    final int x[] = new int[NUMBER_OF_CELLS];
    final int y[] = new int[NUMBER_OF_CELLS];
    int snakeLength = 5;
    int eatenApples;
    int appleX;
    int appleY;
    String direction = "right";
    boolean moving = false;
    Timer timer;    
    static final int DELAY = 90;
    Random random;
    public Color snakeColor1;
    public Color snakeColor2;

    boolean isSpecialBerry = false;
    int specialBerryX;
    int specialBerryY;
    int specialBerryTimer = 0;

    public GamePanel(Color snakeColor1, Color snakeColor2){
        this.snakeColor1 = snakeColor1;
        this.snakeColor2 = snakeColor2;
        random = new Random();
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.BLACK
        );
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        spawnApple();
        moving = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if (moving) {
            for (int i = 0; i < WINDOW_HEIGHT / CELL_SIZE; i++) {
                g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, WINDOW_HEIGHT);
                g.drawLine(0, i * CELL_SIZE, WINDOW_WIDTH, i * CELL_SIZE);
            }
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, CELL_SIZE, CELL_SIZE);
    
            for (int i = 0; i < snakeLength; i++) {
                if (i == 0) {
                    g.setColor(snakeColor1); 
                    g.fillRect(x[i], y[i], CELL_SIZE, CELL_SIZE);
                } else {
                    g.setColor(snakeColor2);
                    g.fillRect(x[i], y[i], CELL_SIZE, CELL_SIZE);
                }
            }
            g.setColor(Color.PINK);
            g.setFont(new Font("Calibri", Font.ITALIC, 35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + eatenApples, (WINDOW_WIDTH - metrics.stringWidth("Score: " + eatenApples)) / 2, g.getFont().getSize());
        } else {
            endOfGame(g);
        }
        if (isSpecialBerry) {
            g.setColor(Color.MAGENTA);
            g.fillOval(specialBerryX, specialBerryY, CELL_SIZE, CELL_SIZE);
        }
    }
    
    
    public void spawnApple() {
        boolean check = true;
        while (check) {
            appleX = random.nextInt((int) (WINDOW_WIDTH / CELL_SIZE)) * CELL_SIZE;
            appleY = random.nextInt((int) (WINDOW_HEIGHT / CELL_SIZE)) * CELL_SIZE;
            for (int i = 0; i < snakeLength; i++) {
                if (x[i] == appleX && y[i] == appleY) {
                    check = true;
                    break;
                } else {
                    check = false;
                }
            }
        }
        if (random.nextInt(5) == 0 && !isSpecialBerry) { 
            check = true;
            while (check) {
            specialBerryX = random.nextInt((int) (WINDOW_WIDTH / CELL_SIZE)) * CELL_SIZE;
            specialBerryY = random.nextInt((int) (WINDOW_HEIGHT / CELL_SIZE)) * CELL_SIZE;
            isSpecialBerry = true;
            for (int i = 0; i < snakeLength; i++) {
                if (x[i] == specialBerryX && y[i] == specialBerryY) {
                    check = true;
                    break;
                } else if (specialBerryX == appleX && specialBerryY == appleY) {
                    check = true;
                }
                else {
                    check = false;
                }
            }
        }
            
        }
    }
    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            snakeLength++;
            eatenApples++;
            spawnApple();
        }
    }

    public void checkSpecialBerry() {
        if (isSpecialBerry && x[0] == specialBerryX && y[0] == specialBerryY) {
            snakeLength++;
            eatenApples++;
            isSpecialBerry = false;
            specialBerryTimer = 150;
            timer.setDelay((int) (timer.getDelay() * 0.2));
        }
        if (specialBerryTimer > 0) {
            specialBerryTimer--;
        } else {
            timer.setDelay(DELAY); 
        }
        
    }
    public void move(){
        for (int i = snakeLength; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

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
    public void checkCollision(){
        	for(int i = snakeLength; i > 0; i--) {
                if((x[0] == x[i]) && (y[0] == y[i])){
                    moving = false;
                }
            }
            if(x[0]< 0) {
                moving = false;
            }
            if(x[0] >= WINDOW_WIDTH) {
                moving = false;
            }
            if(y[0]< 0) {
                moving = false;
            }
            if(y[0] >=WINDOW_HEIGHT) {
                moving = false;
            }
            if (moving == false) {
                timer.stop();
            }
    }
    public void endOfGame(Graphics g) {
        if (isSpecialBerry) {
            isSpecialBerry = false; 
            specialBerryTimer = 0; 
        }
    
        g.setColor(Color.BLUE);
        g.setFont(new Font("Calibri", Font.ITALIC, 35));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + eatenApples, (WINDOW_WIDTH - metrics1.stringWidth("Score: " + eatenApples)) / 2,
                WINDOW_HEIGHT / 2 + 45);
        g.setColor(Color.BLUE);
        g.setFont(new Font("Calibri", Font.ITALIC, 85));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game over", (WINDOW_WIDTH - metrics2.stringWidth("Game over")) / 2, WINDOW_HEIGHT / 2);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (moving) {
            move();
            checkApple();
            checkSpecialBerry();
            checkCollision();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !direction.equals("right")) {
                direction = "left";
            } else if (key == KeyEvent.VK_RIGHT && !direction.equals("left")) {
                direction = "right";
            } else if (key == KeyEvent.VK_UP && !direction.equals("down")) {
                direction = "up";
            } else if (key == KeyEvent.VK_DOWN && !direction.equals("up")) {
                direction = "down";
            }
        }

    }
}
