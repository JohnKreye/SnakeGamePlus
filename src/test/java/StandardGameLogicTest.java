import Game.*;
import Game.Game.*;
import GameBoard.GameBoard;
import GameLogic.GameLogic;
import Snake.Snake;
import org.junit.jupiter.api.Test;

public class StandardGameLogicTest {
    int GAME_BOARD_SIZE = 30;
    Game game = new GameBuilder()
            .addStandardGameLogic()
            .addSquareGameBoard(30)
            .addStandardSnake()
            .forceBuild();

    @Test
    public void testSnakeWallDeath() {

        Snake snake = game.snake;
        GameLogic gameLogic = game.gameLogic;
        GameBoard gameBoard = game.gameBoard;

        snake.setNextDirection(Snake.Direction.UP);

        //Snake starts at 1, 1. So moving up twice should end the game via hitting the wall
        snake.move();
        snake.move();
        gameLogic.checkCollisions();

        assert(gameLogic.gameIsOver());

    }

    @Test
    public void testSnakeSelfCollisionDeath() {

        Snake snake = game.snake;
        GameLogic gameLogic = game.gameLogic;
        GameBoard gameBoard = game.gameBoard;

        //snake doesn't start long enough to kill itself
        snake.grow(2);
        snake.move();
        snake.move();

        //now that it is long enough, turn into self
        snake.setNextDirection(Snake.Direction.DOWN);
        snake.move();

        snake.setNextDirection(Snake.Direction.LEFT);
        snake.move();

        snake.setNextDirection(Snake.Direction.UP);
        snake.move();

        gameLogic.checkCollisions();

        assert(gameLogic.gameIsOver());
    }

    @Test
    public void testObjectCreation() {
        GameLogic gameLogic = game.gameLogic;
        GameBoard gameBoard = game.gameBoard;
        gameLogic.playGame();

        //Standard game logic has 1 standard food on board at all times
        assert(gameBoard.getGameObjects().size() == 1);
    }

}
