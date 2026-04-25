package GameLogic;

import Game.Game;
import GameDisplay.IDynamicSpeedDisplay;
import GameObject.ConsumeableObject;
import GameObject.GameObject;
import GameObject.GameObstacle;
import Snake.Snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ObstacleDodgeGameLogic extends StandardGameLogic {
    //Obstacle Dodge games are similar to standard games except they  will spawn new games whenever food is eaten
    public ObstacleDodgeGameLogic(Game game) {
        super(game);
    }

    @Override
    public void playGame() {
        super.playGame();
    }

    @Override
    protected void addScoreAction() {
        super.addScoreAction();
        Point newObstaclePosition = calculateValidObstaclePosition();
        if (newObstaclePosition != null) {
            game.gameBoard.addGameObject(new GameObstacle(newObstaclePosition));
        }
    }

    private Point calculateValidObstaclePosition() {
        //point needs to be a valid position, but also have free space such that the player can still access the rest of the board.
        //we check this with a breadth fist search; the total number of traversable tiles should be
        //the number of valid tiles minus the number of obstacles in the game.

        Point returnPoint = null;
        Point randomPoint = game.gameBoard.getRandomFreePosition();
        List<Point> validPositions = game.gameBoard.getValidPositions();

        List<Point> obstaclePositions = game.gameBoard.getGameObjects().stream()
                .filter(object -> object instanceof GameObstacle)
                .map(GameObject::getPosition)
                .toList();
        validPositions.removeAll(obstaclePositions);

        List<Point> pointsInFrontOfSnake = getPointsInFrontOfSnake();

        validPositions.removeAll(pointsInFrontOfSnake);

        int numberOfFreePositions = validPositions.size();

        int randomPointIndex = validPositions.indexOf(randomPoint);

        if(randomPointIndex == -1) {
            return calculateValidObstaclePosition();
        }

        List<Point> reOrderedPositions = validPositions.subList(randomPointIndex, numberOfFreePositions);
        reOrderedPositions.addAll(validPositions.subList(0, randomPointIndex));

        for (Point potentialPoint : reOrderedPositions) {
            int traversableTiles = countTraversableTiles(potentialPoint, validPositions);
            if(numberOfFreePositions == traversableTiles) {
                returnPoint = potentialPoint;
                break;
            }
        }

        return returnPoint;
    }

    private List<Point> getPointsInFrontOfSnake() {
        Point snakeHead = game.snake.getHead();
        int snakeHeadX = snakeHead.x;
        int snakeHeadY = snakeHead.y;
        Snake.Direction snakeDirection = game.snake.getDirection();
        int xOffset = 0;
        int yOffset = 0;

        switch (snakeDirection) {
            case UP:
                yOffset = -1;
                break;
            case DOWN:
                yOffset = 1;
                break;
            case LEFT:
                xOffset = -1;
                break;
            case RIGHT:
                xOffset = 1;
                break;
        }

        List<Point> pointsInFrontOfSnake = List.of(
                new Point(snakeHeadX + 1*xOffset, snakeHeadY + 1*yOffset),
                new Point(snakeHeadX + 2*xOffset, snakeHeadY + 2*yOffset),
                new Point(snakeHeadX + 3*xOffset, snakeHeadY + 3*yOffset),
                new Point(snakeHeadX + 4*xOffset, snakeHeadY + 4*yOffset),
                new Point(snakeHeadX + 5*xOffset, snakeHeadY + 5*yOffset)
        );
        return pointsInFrontOfSnake;
    }

    private int countTraversableTiles(Point start, List<Point> validPositions) {
        Queue<Point> queue = new LinkedList<>();
        List<Point> visited = new ArrayList<>();

        queue.add(start);
        visited.add(start);

        int count = 0;

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            count++;

            //Game uses 4-directional movement
            int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

            for (int[] direction : directions) {
                Point neighbor = new Point(current.x + direction[0], current.y + direction[1]);

                // Check if neighbor is traversable and hasn't been visited yet
                if (validPositions.contains(neighbor) && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return count;
    }
}
