package GameBoard;

import Game.*;
import GameObject.GameObject;
import Observers.IObserver;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameBoard {

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
                validPointMap.put(new Point(column + 0, row), true);
                validPointMap.put(new Point(column + 1, row), true);
                validPointMap.put(new Point(column + 2, row), true);
                validPointMap.put(new Point(column + 3, row), true);
                validPointMap.put(new Point(column + 4, row), true);
            }
            for(; column < boardWidth; column++) {
                validPointMap.put(new Point(column, row), true);
            }
        }
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
        return new ArrayList<>(
            validPointMap.entrySet().stream()
            .filter(Map.Entry::getValue)
            .map(Map.Entry::getKey)
            .toList()
        );
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

    public Point getRandomFreePosition() {

        List<Point> validPoints = getValidPositions();

        int randomIndex = random.nextInt(validPoints.size());
        Point randomPoint = validPoints.get(randomIndex);

        if (snakeOnPoint(randomPoint) || objectOnPoint(randomPoint))
            return getRandomFreePosition();
        else
            return randomPoint;
    }

    //needed by some boards, but not all
    public void restart() {}

}
