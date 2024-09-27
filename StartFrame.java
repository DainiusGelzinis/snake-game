import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class OvalButton extends JButton {
    public OvalButton(String text) {
        super(text);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.RED);
        } else {
            g.setColor( new Color(255, 130, 130));
        }
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
    }
}

public class StartFrame extends JFrame {

    private JComboBox<String> colorComboBox;
    
    public StartFrame() {
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(145, 180, 255)); 
        JLabel colorLabel = new JLabel("Select Snake Color:");
        colorLabel.setBounds(90, 50, 150, 20);
        this.add(colorLabel);

        String[] colors = {"Blue", "Green", "Red"};
        colorComboBox = new JComboBox<>(colors);
        colorComboBox.setBounds(90, 80, 100, 20);
        this.add(colorComboBox);

        OvalButton startButton = new OvalButton("Start");
        startButton.setBounds(90, 120, 100, 40); 
        startButton.setForeground(Color.BLACK); 
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                String selectedColor = (String) colorComboBox.getSelectedItem();
                Color snakeColor1 = Color.GREEN;
                Color snakeColor2 = new Color(40, 170, 0);
                if (selectedColor.equals("Blue")) {
                    snakeColor1 = new Color(80, 100, 240);
                    snakeColor2 = Color.BLUE;
                } else if (selectedColor.equals("Green")) {
                    snakeColor1 = Color.GREEN;
                    snakeColor2 = new Color(40, 170, 0);
                } else if (selectedColor.equals("Red")) {
                    snakeColor1 = new Color(255, 130, 130);
                    snakeColor2 = Color.RED;
                }
                new GameFrame(snakeColor1, snakeColor2);
            }
        });

        this.add(startButton);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new StartFrame();
    }
}
