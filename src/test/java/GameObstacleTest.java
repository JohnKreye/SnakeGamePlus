import Game.Game;
import GameBoard.GameBoard;
import GameLogic.GameLogic;
import GameObject.GameObstacle;
import Snake.Snake;
import org.junit.jupiter.api.Test;

import java.awt.Point;

public class GameObstacleTest {

    int GAME_BOARD_SIZE = 30;

    Game game = new Game.GameBuilder()
            .addSquareGameBoard(GAME_BOARD_SIZE)
            .addStandardGameLogic()
            .addStandardSnake()
            .forceBuild();

    @Test
    public void testGameObstacleInteraction() {
        GameBoard gameBoard = game.gameBoard;
        GameLogic gameLogic = game.gameLogic;
        Snake snake = game.snake;
        Point head = snake.getHead();
        Point inFrontOfSnake = new Point(head.x + 1, head.y);
        gameBoard.addGameObject(new GameObstacle(inFrontOfSnake));

        snake.setNextDirection(Snake.Direction.RIGHT);
        snake.move();
        gameLogic.checkGameObjects();

        assert (gameLogic.gameIsOver());
    }
}
