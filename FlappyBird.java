import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Font;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.JFrame;

public class FlappyBird implements ActionListener, KeyListener {

    public static void main(String[] args) throws Exception {
        flappyBird = new FlappyBird();

    }

    public static FlappyBird flappyBird;
    public BirdRenderer birdRenderer;
    public Rectangle bird;
    public int ticks, yMotion, score;
    public ArrayList<Rectangle> obstacleList;
    public Random random;
    public boolean gameOver, gameStarted;
    public final int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 800;

    public FlappyBird() {
        JFrame jFrame = new JFrame();
        Timer timer = new Timer(20, this);
        birdRenderer = new BirdRenderer();
        random = new Random();
        jFrame.add(birdRenderer);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        jFrame.addKeyListener(this);
        jFrame.setResizable(false);
        jFrame.setTitle("Flappy Bird Game");
        jFrame.setVisible(true);

        bird = new Rectangle(SCREEN_WIDTH / 2 - 10, SCREEN_HEIGHT / 2 - 10, 20, 20);
        obstacleList = new ArrayList<>();

        addObstacle(true);
        addObstacle(true);
        addObstacle(true);
        addObstacle(true);

        timer.start();

    }

    public void makeJump() {
        if (gameOver) {
            bird = new Rectangle(SCREEN_WIDTH / 2 - 10, SCREEN_HEIGHT / 2 - 10, 20, 20);
            obstacleList.clear();
            yMotion = 0;
            score = 0;
            addObstacle(true);
            addObstacle(true);
            addObstacle(true);
            addObstacle(true);
            gameOver = false;

        }

        if (!gameStarted) {
            gameStarted = true;
        } else if (!gameOver) {
            if (yMotion > 0) {
                yMotion = 0;
            }
            yMotion -= 10;

        }
    }

    public void addObstacle(boolean startGame) {
        int space = 300;
        int width = 100;
        int height = 50 + random.nextInt(300);

        if (startGame) {
            obstacleList.add(new Rectangle(SCREEN_WIDTH + width + obstacleList.size() * 300,
                    SCREEN_HEIGHT - height - 120, width, height));
            obstacleList.add(new Rectangle(SCREEN_WIDTH + width + (obstacleList.size() - 1) * 300, 0, width,
                    SCREEN_HEIGHT - height - space));

        } else {
            obstacleList.add(new Rectangle(obstacleList.get(obstacleList.size() - 1).x + 600,
                    SCREEN_HEIGHT - height - 120, width, height));
            obstacleList.add(new Rectangle(obstacleList.get(obstacleList.size() - 1).x, 0, width,
                    SCREEN_HEIGHT - height - space));

        }
    }

    public void repaint(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        g.setColor(Color.ORANGE);
        g.fillRect(0, SCREEN_HEIGHT - 120, SCREEN_WIDTH, 120);

        g.setColor(Color.green);
        g.fillRect(0, SCREEN_HEIGHT - 120, SCREEN_WIDTH, 20);

        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.height, bird.width);

        for (Rectangle obstacle : obstacleList) {
            paintObstacle(g, obstacle);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 80));

        if (!gameStarted) {
            g.drawString("click to begin", 75, SCREEN_HEIGHT / 2 - 50);
        }
        if (gameOver)
            g.drawString("GameOver", 100, SCREEN_HEIGHT / 2 - 50);

        if (!gameOver && gameStarted) {
            g.drawString(String.valueOf(score), SCREEN_WIDTH / 2 - 25, 100);
        }

    }

    public void paintObstacle(Graphics g, Rectangle obstacle) {
        g.setColor(Color.green.darker().darker());
        g.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 10;
        ticks++;
        if (gameStarted) {
            for (int i = 0; i < obstacleList.size(); i++) {
                Rectangle obstacle = obstacleList.get(i);
                obstacle.x -= speed;
            }
            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }
            for (int i = 0; i < obstacleList.size(); i++) {
                Rectangle obstacle = obstacleList.get(i);
                if (obstacle.x + obstacle.width < 0) {
                    obstacleList.remove(obstacle);
                    if (obstacle.y == 0) {
                        addObstacle(false);
                    }
                }
            }
            bird.y += yMotion;

            for (Rectangle obstacle : obstacleList) {
                if (obstacle.y == 0 && bird.x + bird.width / 2 > obstacle.x + obstacle.width / 2 - 10
                        && bird.x + bird.width / 2 < obstacle.x + obstacle.width / 2 + 10) {
                    score++;
                }
                if (obstacle.intersects(bird)) {
                    gameOver = true;
                    if (bird.x <= obstacle.x) {
                        bird.x = obstacle.x - bird.width;
                    } else {
                        if (obstacle.y != 0) {
                            bird.y = obstacle.y - bird.height;
                        } else if (bird.y < obstacle.height) {
                            bird.y = obstacle.height;
                        }
                    }

                }
            }

            if (bird.y > SCREEN_HEIGHT - 120 || bird.y < 0) {
                gameOver = true;
            }
            if (bird.y + yMotion >= SCREEN_HEIGHT - 120) {
                bird.y = SCREEN_HEIGHT - 120 - bird.height;
            }
        }
        birdRenderer.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            makeJump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
