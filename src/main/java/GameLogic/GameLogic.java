package GameLogic;

import java.awt.*;
import java.awt.event.KeyEvent;
import Game.*;
import GameObject.*;
import Observers.IKeyObserver;
import Snake.Snake;
import java.util.List;
import java.util.Random;

public abstract class GameLogic implements IKeyObserver {

    protected static int STARTING_SCORE = 0;

    protected Game game;

    protected Random random = new Random();

    protected int score = STARTING_SCORE;

    protected boolean gameOver = false;
    protected boolean gamePaused = false;

    public GameLogic(Game game) {
        random.setSeed(System.currentTimeMillis());
        this.game = game;
    }

    public void playGame() {
        if(game.gameDisplay != null) {
            game.gameDisplay.startDisplay();
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_UP: game.snake.setNextDirection(Snake.Direction.UP); break;
            case KeyEvent.VK_DOWN: game.snake.setNextDirection(Snake.Direction.DOWN); break;
            case KeyEvent.VK_LEFT: game.snake.setNextDirection(Snake.Direction.LEFT); break;
            case KeyEvent.VK_RIGHT: game.snake.setNextDirection(Snake.Direction.RIGHT); break;
            case KeyEvent.VK_SPACE: if (gameOver) restart(); break;
            case KeyEvent.VK_ESCAPE: gamePaused = !gamePaused; break;
        }
    }

    public void checkCollisions() {
        Point head = game.snake.getHead();

        // Wall collisions
        if (game.gameBoard.isValidPosition(head) == false) {
            endGame();
        }

        // Self collisions
        for (int i = 1; i < game.snake.getBodyPositions().size(); i++) {
            if (head.equals(game.snake.getBodyPositions().get(i))) {
                endGame();
                break;
            }
        }
    }

    public void checkGameObjects() {
        Point head = game.snake.getHead();
        List<GameObject> gameObjects = game.gameBoard.getGameObjects();
        for(GameObject gameObject : gameObjects) {
            if (head.equals(gameObject.getPosition())) {
                gameObject.interact(game);
            }
        }
    }

    protected void restart() {
        score = STARTING_SCORE;
        game.snake.restart();
        game.gameDisplay.restartDisplay();
        game.gameBoard.restart();
        gameOver = false;
        playGame();
    }

    public boolean gameIsOver() { return gameOver; }

    public boolean gameIsPaused() { return gamePaused; }

    public String getScore() { return String.valueOf(score); }

    public void endGame() {
        gameOver = true;
    }

    public void removeConsumableObject(ConsumeableObject consumeableObject) {
        game.gameBoard.removeGameObject(consumeableObject);
    }

    public void addScore(int score) {
        addScoreAction();
        this.score += score;
    }

    protected abstract void addScoreAction();
}
