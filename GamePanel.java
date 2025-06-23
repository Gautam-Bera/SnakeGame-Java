import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    // Game constants
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNIT = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 110;

    // Snake body parts and direction
    private final List<Point> snake = new ArrayList<>();
    private int bodyParts = 1; // Initial snake length
    private char direction = 'R';

    // Food location and score
    private Point food;
    private int score;

    // Game state
    private boolean running = false;
    private Timer timer;  //updates
    private Random random;


    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }


    public void startGame() {
        snake.clear();  //clear memory
        snake.add(new Point(0, 0)); // Initial head position
        bodyParts = 1;
        direction = 'R';

        newFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        score = 0;
    }


    @Override
    public void paintComponent(Graphics g) {
        //clear the screen
        super.paintComponent(g);
        draw(g);
    }


    public void draw(Graphics g) {
        if (running) {
            // Draw the food
            g.setColor(Color.red);
            g.fillOval(food.x, food.y, UNIT_SIZE, UNIT_SIZE);

            // Draw the snake
            for (int i = 0; i < bodyParts; i++) {
                if (i < snake.size()) {
                    Point part = snake.get(i);
                    if (i == 0) {
                        g.setColor(new Color(36, 110, 0)); // Snake head
                    } else {
                        g.setColor(new Color(45, 180, 0)); // Snake body
                    }
                    g.fill3DRect(part.x, part.y, UNIT_SIZE, UNIT_SIZE, true);
                }
            }

            // Draw the score
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + score, (SCREEN_WIDTH - metrics.stringWidth("Score: " + score)) / 2, g.getFont().getSize());

        } else {
            gameOver(g);
        }
    }


    public void newFood() {
        while (true) {
            int foodX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            int foodY = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            food = new Point(foodX, foodY);
            if (!snake.contains(food)) break;
        }
    }


    public void move() {
        Point newHead = new Point(snake.get(0));
        switch (direction) {
            case 'U':
                newHead.y -= UNIT_SIZE;
                break;
            case 'D':
                newHead.y += UNIT_SIZE;
                break;
            case 'L':
                newHead.x -= UNIT_SIZE;
                break;
            case 'R':
                newHead.x += UNIT_SIZE;
                break;
        }
        snake.add(0, newHead);

        if (snake.size() > bodyParts) {
            snake.remove(snake.size() - 1);
        }

    }


    public void foodEaten() {
        if (snake.get(0).equals(food)) {
            bodyParts++;
            score++;
            newFood();
        }
    }


    public void checkCollisions() {
        Point head = snake.get(0);

        // Check if head collides with walls
        if (head.x < 0 || head.x >= SCREEN_WIDTH || head.y < 0 || head.y >= SCREEN_HEIGHT) {
            running = false;
        }

        // Check if head collides with body
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                running = false;
                break;
            }
        }

        if (!running) {
            timer.stop();
        }
    }


    public void gameOver(Graphics g) {
        // Game Over
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2 - 50);

        // Final Score
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Final Score: " + score, (SCREEN_WIDTH - metrics2.stringWidth("Final Score: " + score)) / 2, g.getFont().getSize());

        // Restart
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press Spacebar to Restart", (SCREEN_WIDTH - metrics3.stringWidth("Press Spacebar to Restart")) / 2, SCREEN_HEIGHT / 2 + 50);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            foodEaten();
            checkCollisions();
        }
        repaint();
    }

    //Inner class to handle keyboard input for controlling the snake.
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    if (!running) {
                        startGame();
                    }

                    break;
            }
        }
    }
}

