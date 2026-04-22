package GameBoard;

import Game.*;
import GameObject.GameObject;
import Observers.IObserver;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class GameBoard implements IObserver {

    protected int boardWidth;
    protected int boardHeight;
    protected Map<Point, Boolean> validPointMap = new HashMap<>();
    protected Map<Point, GameObject> gameObjectMap = new HashMap<>();
    protected Game game;
    protected Random random = new Random();

    public GameBoard(int boardWidth, int boardHeight, Game game) {
        this.game = game;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        constructGameBoard(boardWidth, boardHeight);
    }

    protected void constructGameBoard(int boardWidth, int boardHeight) {
        for(int row = 0; row < boardHeight; row++) {
            int column = 0;
            for(; column < boardWidth - 5; column += 5) {
                validPointMap.put(new Point(row, column + 0), true);
                validPointMap.put(new Point(row, column + 1), true);
                validPointMap.put(new Point(row, column + 2), true);
                validPointMap.put(new Point(row, column + 3), true);
                validPointMap.put(new Point(row, column + 4), true);
            }
            column -= 5;
            for(; column < boardWidth; column++) {
                validPointMap.put(new Point(row, column), true);
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

    public List<GameObject> getGameObjects() { return gameObjectMap.values().stream().toList();}

    public void addGameObject(GameObject gameObject) {
        gameObjectMap.put(gameObject.getPosition(), gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjectMap.remove(gameObject.getPosition());
    }

    public void clearGameObjects() { gameObjectMap = new HashMap<>(); }

    public List<Point> getValidPositions() {
        return validPointMap.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();
    }

    public boolean isValidPosition(Point point) {
        Boolean valid = validPointMap.get(point);
        return (valid != null) ? valid : false;
    }

    private boolean snakeOnPoint(Point point) {
        return game.snake.getBodyPositions().contains(point);
    }

    private boolean objectOnPoint(Point point) {
        return gameObjectMap.containsKey(point);
    }

    public Point getRandomValidPosition() {

        List<Point> validPoints = getValidPositions();

        int randomIndex = random.nextInt(0, validPoints.size());
        Point randomPoint = validPoints.get(randomIndex);

        if (snakeOnPoint(randomPoint) || objectOnPoint(randomPoint))
            return getRandomValidPosition();
        else
            return randomPoint;
    }
}
