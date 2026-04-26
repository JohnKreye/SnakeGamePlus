package GameLogic;

import java.awt.*;

import Game.*;
import GameDisplay.IGameDisplay;
import GameObject.*;
import Observers.IKeyObserver;
import Observers.IObserver;
import Snake.Snake;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import java.util.List;
import java.util.Random;

public abstract class GameLogic implements IKeyObserver, IObserver {

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

    //needed for some games
    public void playGame() {}

    @Override
    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case NativeKeyEvent.VC_UP: game.snake.setNextDirection(Snake.Direction.UP); break;
            case NativeKeyEvent.VC_DOWN: game.snake.setNextDirection(Snake.Direction.DOWN); break;
            case NativeKeyEvent.VC_LEFT: game.snake.setNextDirection(Snake.Direction.LEFT); break;
            case NativeKeyEvent.VC_RIGHT: game.snake.setNextDirection(Snake.Direction.RIGHT); break;
            case NativeKeyEvent.VC_ESCAPE: gamePaused = !gamePaused; break;
            case NativeKeyEvent.VC_SPACE: if(gameOver) restart(); break;
            case NativeKeyEvent.VC_DELETE: System.exit(0);
        }
    }

    @Override
    public void update() {
        if (!(game.gameLogic.gameIsOver() || game.gameLogic.gameIsPaused())) {
            game.snake.move();
            game.gameLogic.checkCollisions();
            game.gameLogic.checkGameObjects();
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

    public void restart() {
        score = STARTING_SCORE;
        game.snake.restart();
        for(IGameDisplay display : game.gameDisplays) {
            display.restartDisplay();
        }
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
