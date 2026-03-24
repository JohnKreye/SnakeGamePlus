import Game.Game;
import Game.Game.*;
import Snake.Snake;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class SnakeTest {

    private void testMove(Snake snake, Snake.Direction direction) {
        Point headPositionBefore = snake.getHead();

        boolean directionChanged = snake.setNextDirection(direction);

        if (directionChanged == false) {
            switch (direction) {
                case UP -> {
                    assert (snake.getDirection() == Snake.Direction.DOWN);
                }
                case DOWN -> {
                    assert (snake.getDirection() == Snake.Direction.UP);
                }
                case LEFT -> {
                    assert (snake.getDirection() == Snake.Direction.RIGHT);
                }
                case RIGHT -> {
                    assert (snake.getDirection() == Snake.Direction.LEFT);
                }
            }
        }

        else {
            snake.move();
            Point headPositionAfter = snake.getHead();

            int xOffset = 0;
            int yOffset = 0;
            switch (direction) {
                case UP -> yOffset = -1;
                case DOWN -> yOffset = 1;
                case LEFT -> xOffset = -1;
                case RIGHT -> xOffset = 1;
            }

            assert(headPositionBefore.x + xOffset == headPositionAfter.x);
            assert(headPositionBefore.y + yOffset == headPositionAfter.y);
        }
    }

    @Test
    public void allPossibleMoveTest() {
        for(Snake.Direction direction1 : Snake.Direction.values()) {
            for(Snake.Direction direction2 : Snake.Direction.values()) {
                Game manualGame = new GameBuilder()
                        .addStandardSnake()
                        .forceBuild();
                Snake snake = manualGame.snake;
                testMove(snake, direction1);
                testMove(snake, direction2);
            }
        }
    }

}
