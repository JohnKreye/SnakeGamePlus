package GameBoard;

import Game.*;
import GameObject.GameObject;
import Observers.IObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class GameBoard implements IObserver {

    protected int boardWidth;
    protected int boardHeight;
    protected Map<Point, Boolean> boardMap = new HashMap<>();
    protected List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    protected Game game;

    public GameBoard(int boardWidth, int boardHeight, Game game) {
        this.game = game;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        for(int row = 0; row < boardHeight; row++) {
            int column = 0;
            for(; column < boardWidth; column += 5) {
                boardMap.put(new Point(row, column + 0), true);
                boardMap.put(new Point(row, column + 1), true);
                boardMap.put(new Point(row, column + 2), true);
                boardMap.put(new Point(row, column + 3), true);
                boardMap.put(new Point(row, column + 4), true);
            }
            column -= 5;
            for(; column < boardWidth; column++) {
                boardMap.put(new Point(row, column), true);
            }
        }
    }

    @Override
    public void update() {
        if (game.gameLogic.gameIsOver())
            return;

        game.snake.move();
        game.gameLogic.checkCollisions();
        game.gameLogic.checkGameObjects();

        game.gameDisplay.updateDisplay();
    }



    public int getWidth() { return boardWidth; }
    public int getHeight() { return boardHeight; }

    public List<GameObject> getGameObjects() { return gameObjects; }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public void clearGameObjects() {
        gameObjects = new ArrayList<>();
    }

    public boolean isValidPosition(Point head) {
        Boolean valid = boardMap.get(head);
        return (valid != null) ? valid : false;
    }
}
