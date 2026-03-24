import Game.Game.*;
import Game.*;
import GameBoard.*;
import GameObject.*;
import Snake.Snake;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class StandardFoodTest {

    int GAME_BOARD_SIZE = 30;

    Game game = new GameBuilder()
            .addSquareGameBoard(30)
            .addStandardGameLogic()
            .addStandardSnake()
            .forceBuild();


    @Test
    public void testStandardFoodConsumption() {
        GameBoard gameBoard = game.gameBoard;
        Snake snake = game.snake;
        int previousSnakeSize = snake.getSize();

        Point snakeHead = snake.getHead();
        Point pointInFrontOfSnake = new Point(snakeHead.x + 1, snakeHead.y);

        GameObject foodItem = new StandardFood(pointInFrontOfSnake);
        gameBoard.addGameObject(foodItem);

        snake.move();
        game.gameLogic.checkGameObjects();

        assert (snake.getSize() == previousSnakeSize + 1);

    }
}
