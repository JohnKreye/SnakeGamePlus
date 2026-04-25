package GameDisplay;

import GameObject.*;
import Game.*;
import Observers.*;
import Snake.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class JavaXSwingPanel extends JPanel {

    private static final int TILE_SIZE = 25;
    private final int SCORE_X_OFFSET = 10;
    private final int SCORE_Y_OFFSET = 20;

    private List<IKeyObserver> keyObservers = new ArrayList<>();
    private List<IObserver> observers = new ArrayList<>();
    private int gridWidth;
    private int gridHeight;

    private Timer timer;
    private Game game;

    @Override
    protected void paintComponent(Graphics graphics) {
        FontMetrics fontMetrics = graphics.getFontMetrics();
        super.paintComponent(graphics);

        drawValidTiles(graphics);
        drawGameObjects(graphics);
        drawSnake(graphics);
        drawText(graphics, fontMetrics);

    }

    private void drawValidTiles(Graphics graphics) {
        List<Point> validPoints = game.gameBoard.getValidPositions();
        int size = validPoints.size();
        int index = 0;
        for(; index < size - 5; index += 5) {
            drawValidTile(validPoints.get(index + 0), graphics);
            drawValidTile(validPoints.get(index + 1), graphics);
            drawValidTile(validPoints.get(index + 2), graphics);
            drawValidTile(validPoints.get(index + 3), graphics);
            drawValidTile(validPoints.get(index + 4), graphics);
        }
        for(; index < size; index ++) {
            drawValidTile(validPoints.get(index), graphics);
        }
    }

    private void drawValidTile(Point point, Graphics graphics) {
        //even summed coordinates will have different color from odd summed coordinates
        //in other words a checkered pattern
        Color color1 = Color.DARK_GRAY.darker();
        Color color2 = color1.darker();
        int coordinateSum = point.x + point.y;


        if(coordinateSum % 2 == 0)
            drawPoint(point, color1, graphics);
        else
            drawPoint(point, color2, graphics);
    }

    private void drawText(Graphics graphics, FontMetrics fontMetrics) {
        graphics.setColor(Color.WHITE);
        graphics.drawString("Score: " + game.gameLogic.getScore(), SCORE_X_OFFSET, SCORE_Y_OFFSET);

        if (game.gameLogic.gameIsOver()) {
            String gameOver = "GAME OVER : SCORE " + game.gameLogic.getScore();
            String pressSpace = "PRESS SPACE TO RESTART";
            graphics.drawString(gameOver, (getDisplayWidth() - fontMetrics.stringWidth(gameOver))/ 2, (getDisplayHeight()) / 2);
            graphics.drawString(pressSpace, (getDisplayWidth() - fontMetrics.stringWidth(pressSpace)) / 2, (int) (getDisplayHeight() / 2 + fontMetrics.getHeight()*1.5));        }

        else if (game.gameLogic.gameIsPaused()) {
            String gamePaused = "GAME PAUSED";
            graphics.drawString(gamePaused, (getDisplayWidth() - fontMetrics.stringWidth(gamePaused)) / 2, (getDisplayHeight()) / 2);
        }
    }

    private void drawSnake(Graphics graphics) {
        Snake snake = game.snake;
        List<Color> snakeColorPattern = snake.getColorPattern();
        int totalColors = snakeColorPattern.size();
        int colorIndex = 0;

        for (Point bodyPartPosition : snake.getBodyPositions()) {
            drawPoint(bodyPartPosition, snakeColorPattern.get(colorIndex), graphics);
            colorIndex ++;

            if (colorIndex >= totalColors) {
                colorIndex = 0;
            }
        }
    }

    private void drawGameObjects(Graphics graphics) {
        List<GameObject> gameObjects = game.gameBoard.getGameObjects();
        for (GameObject gameObject : gameObjects) {
            Point objectPosition = gameObject.getPosition();
            drawPoint(objectPosition, gameObject.getColor(), graphics);
        }
    }

    private void drawPoint(Point point, Color color, Graphics graphics) {
        point = new Point(point.x + 1, point.y +1); //added a black border, so everything will be offset
        graphics.setColor(color);
        graphics.fillRect(point.x * TILE_SIZE, point.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    private int getDisplayWidth() {
        return gridWidth * TILE_SIZE;
    }

    private int getDisplayHeight() {
        return gridHeight * TILE_SIZE;
    }

    public void changeSpeed(int speed) {
        setGameSpeed(speed);
    }

    public JavaXSwingPanel(Game game, int refreshSpeed) {
        this.game = game;
        this.gridWidth = game.gameBoard.getWidth()+2;
        this.gridHeight = game.gameBoard.getHeight()+2;

        setPreferredSize(new Dimension(gridWidth * TILE_SIZE, gridHeight * TILE_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                notifyKeyObservers(event);
            }
        });

        //Start game loop
        setGameSpeed(refreshSpeed);
    }

    public void restart() {
        this.gridWidth = game.gameBoard.getWidth()+2;
        this.gridHeight = game.gameBoard.getHeight()+2;

        setPreferredSize(new Dimension(gridWidth * TILE_SIZE, gridHeight * TILE_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);

        observers.clear();
    }

    private void setGameSpeed(int refreshSpeed) {
        System.out.println("Requested Speed: " + refreshSpeed);

        if (timer == null) {
            timer = new Timer(refreshSpeed, e -> {
                notifyObservers();
            });
            timer.start();
        }
        else {
            timer.setDelay(refreshSpeed);
            timer.setInitialDelay(0);
            timer.restart();
            System.out.println("Timer Delay is now: " + timer.getDelay());
        }
    }

    private void notifyKeyObservers(KeyEvent event) {
        for(IKeyObserver keyObserver : keyObservers) {
            keyObserver.keyPressed(event);
        }
    }

    public void notifyObservers() {
        for(IObserver observer : observers) {
            observer.update();
        }
    }

    public void addKeyObserver(IKeyObserver keyObserver) {
        keyObservers.add(keyObserver);
    }

    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    public void removeKeyObserver(IKeyObserver keyObserver) {
        keyObservers.remove(keyObserver);
    }

    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    public void close() {
        timer.stop();
    }
}
