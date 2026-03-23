package GameDisplay;

import GameBoard.*;
import GameLogic.*;
import GameObject.*;
import Game.*;
import GameDisplay.*;
import Observers.*;
import Snake.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class JavaXSwingPanel extends JPanel implements DynamicSpeedDisplay {

    private static final int TILE_SIZE = 25;

    private List<KeyObserver> keyObservers = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();
    private final int gridWidth;
    private final int gridHeight;

    private Timer timer;
    private Game game;

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        //Draw GameObjects
        List<GameObject> gameObjects = game.gameBoard.getGameObjects();
        for (GameObject gameObject : gameObjects) {
            graphics.setColor(gameObject.getColor());
            Point objectPosition = gameObject.getPosition();
            graphics.fillRect(objectPosition.x * TILE_SIZE, objectPosition.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        //Draw Snake.Snake
        Snake snake = game.snake;
        List<Color> snakeColorPattern = snake.getColorPattern();
        int totalColors = snakeColorPattern.size();
        int colorIndex = 0;

        for (Point bodyPartPosition : snake.getBodyPositions()) {
            graphics.setColor(snakeColorPattern.get(colorIndex));
            graphics.fillRect(bodyPartPosition.x * TILE_SIZE, bodyPartPosition.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            colorIndex ++;

            if (colorIndex >= totalColors) {
                colorIndex = 0;
            }
        }

        // Draw Game.Game Over & Score
        graphics.setColor(Color.WHITE);
        graphics.drawString("Score: " + game.gameLogic.getScore(), 10, 20);

        if (game.gameLogic.gameIsOver()) {
            graphics.drawString("GAME OVER", (gridHeight * TILE_SIZE) / 2 - 35, (gridWidth * TILE_SIZE) / 2);
        }

        else if (game.gameLogic.gameIsPaused()) {
            graphics.drawString("GAME PAUSED", (gridHeight * TILE_SIZE) / 2 - 35, (gridWidth * TILE_SIZE) / 2);
        }
    }

    @Override
    public void changeSpeed(int speed) {
        setGameSpeed(speed);
    }

    public JavaXSwingPanel(Game game, int refreshSpeed) {
        this.game = game;
        this.gridWidth = game.gameBoard.getWidth();
        this.gridHeight = game.gameBoard.getHeight();

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

    private void setGameSpeed(int refreshSpeed) {
        if(timer != null)
            timer.stop();
        timer = new Timer(refreshSpeed, e -> {
            if(!game.gameLogic.gameIsPaused()) {
                notifyObservers();
            }
            if (game.gameLogic.gameIsOver()) {
                timer.stop();
            }
        });
        timer.start();
    }

    private void notifyKeyObservers(KeyEvent event) {
        for(KeyObserver keyObserver : keyObservers) {
            keyObserver.keyPressed(event);
        }
    }

    public void notifyObservers() {
        for(Observer observer : observers) {
            observer.update();
        }
    }

    public void addKeyObserver(KeyObserver keyObserver) {
        keyObservers.add(keyObserver);
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeKeyObserver(KeyObserver keyObserver) {
        keyObservers.remove(keyObserver);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }



}
