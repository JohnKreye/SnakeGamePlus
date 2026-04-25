package GameLogic;

import Game.Game;
import GameBoard.*;
import GameDisplay.JavaXSwingDisplay;
import GameObject.ConsumeableObject;
import Snake.TransformingSnake;
import Snake.*;

import java.awt.event.KeyEvent;
import java.util.List;

public class ChaosGameLogic extends DecoratedGameLogic {
    private static int MAZE_WIDTH = 3;

    private static List<String> GAME_LOGIC_OPTIONS = List.of(
            "StandardGameLogic",
            "ObstacleDodgeGameLogic",
            "QuickGrowGameLogic"
    );
    private static List<String> GAME_BOARD_OPTIONS = List.of(
            "GameBoard",
            "SquareGameBoard",
            "RingGameBoard",
            "MazeGameBoard"
    );
    private static List<Integer> BOARD_DIMENSION_OPTIONS = List.of(
            7, 11, 15, 19, 23, 27, 31, 35
    );
    private static List<String> SNAKE_OPTIONS = List.of(
            "Snake",
            "AcceleratingSnake",
            "DeceleratingSnake"
    );

    public ChaosGameLogic(Game game) {
        super(game, null);
        innerGameLogic = getRandomGameLogic();
        game.gameDisplay = new JavaXSwingDisplay(game);
        game.gameBoard = getRandomGameBoard();
        game.snake = new TransformingSnake(getRandomSnake());
    }

    @Override
    protected void restart() {
        score = STARTING_SCORE;

        TransformingSnake transformingSnake = (TransformingSnake) game.snake;
        transformingSnake.transFormInto(getRandomSnake());
        transformingSnake.restart();

        innerGameLogic = getRandomGameLogic();
        game.gameBoard = getRandomGameBoard();

        game.gameDisplay.restartDisplay();

        innerGameLogic.gameOver = false;
        playGame();
    }

    @Override
    public void addScore(int score) {
        TransformingSnake transformingSnake = (TransformingSnake) game.snake;
        transformingSnake.transFormInto(getRandomSnake());
        int oldScore = innerGameLogic.score;
        innerGameLogic = getRandomGameLogic();
        super.addScore(oldScore + score);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_UP: game.snake.setNextDirection(Snake.Direction.UP); break;
            case KeyEvent.VK_DOWN: game.snake.setNextDirection(Snake.Direction.DOWN); break;
            case KeyEvent.VK_LEFT: game.snake.setNextDirection(Snake.Direction.LEFT); break;
            case KeyEvent.VK_RIGHT: game.snake.setNextDirection(Snake.Direction.RIGHT); break;
            case KeyEvent.VK_SPACE: if (innerGameLogic.gameOver) restart(); break;
            case KeyEvent.VK_ESCAPE: innerGameLogic.gamePaused = !innerGameLogic.gamePaused; break;
        }
    }

    private GameLogic getRandomGameLogic() {
        int randomIndex = random.nextInt(GAME_LOGIC_OPTIONS.size());
        String gameLogicString = GAME_LOGIC_OPTIONS.get(randomIndex);

        return switch (gameLogicString) {
            case "StandardGameLogic" -> new StandardGameLogic(game);
            case "ObstacleDodgeGameLogic" -> new ObstacleDodgeGameLogic(game);
            case "QuickGrowGameLogic" -> new StandardGameLogic(game, 5);
            default ->
                    throw new RuntimeException("ChaosGameLogic: Error: GameLogic \"" + gameLogicString + "\" does not have a registered switch statement in getRandomGameLogic().");
        };

    }

    private GameBoard getRandomGameBoard() {
        int randomIndex = random.nextInt(GAME_BOARD_OPTIONS.size());
        String gameBoardString = GAME_BOARD_OPTIONS.get(randomIndex);

        randomIndex =random.nextInt(BOARD_DIMENSION_OPTIONS.size());
        int randomBoardDimension1 = BOARD_DIMENSION_OPTIONS.get(randomIndex);

        randomIndex =random.nextInt(BOARD_DIMENSION_OPTIONS.size());
        int randomBoardDimension2 = BOARD_DIMENSION_OPTIONS.get(randomIndex);

        return switch (gameBoardString) {
            case "GameBoard" -> new GameBoard(randomBoardDimension1, randomBoardDimension2, game);
            case "SquareGameBoard" -> new SquareGameBoard(randomBoardDimension1, game);
            case "RingGameBoard" -> new RingGameBoard(randomBoardDimension1, randomBoardDimension2, game);
            case "MazeGameBoard" -> new MazeGameBoard(randomBoardDimension1, randomBoardDimension2, MAZE_WIDTH, game);
            default ->
                    throw new RuntimeException("ChaosGameLogic: Error: GameBoard \"" + gameBoardString + "\" does not have a registered switch statement in getRandomGameBoard().");
        };
    }

    private Snake getRandomSnake() {
        int randomIndex = random.nextInt(SNAKE_OPTIONS.size());
        String snakeString = SNAKE_OPTIONS.get(randomIndex);

        return switch (snakeString) {
            case "Snake" -> new Snake(game);
            case "AcceleratingSnake" -> new AcceleratingSnake(game);
            case "DeceleratingSnake" -> new DeceleratingSnake(game);
            default ->
                    throw new RuntimeException("ChaosGameLogic: Error: Snake \"" + snakeString + "\" does not have a registered switch statement in getRandomSnake().");
        };
    }

}
