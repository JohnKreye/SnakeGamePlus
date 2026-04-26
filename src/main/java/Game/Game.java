package Game;

import GameBoard.*;
import GameDisplay.*;
import GameEngine.GameEngine;
import GameEngine.*;
import GameLogic.*;
import Snake.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public GameLogic gameLogic;
    public GameBoard gameBoard;
    public List<IGameDisplay> gameDisplays = new ArrayList<>();
    public GameEngine gameEngine;
    public Snake snake;

    private boolean directionQueued = false;
    private Snake.Direction queuedDirection = Snake.Direction.RIGHT;

    protected Game() {}

    public void playGame() {
        gameLogic.playGame();
    }

    public void queueSnakeDirectionChange(Snake.Direction direction) {
        queuedDirection = direction;
        directionQueued = true;
    }

    public static class GameBuilder {
        private Game newGame;

        public GameBuilder() {
            this.newGame = new Game();
        }

        public Game build() {

            boolean logicNull = newGame.gameLogic == null;
            boolean boardNull = newGame.gameBoard == null;
            boolean displayNull = newGame.gameDisplays.isEmpty();
            boolean snakeNull = newGame.snake == null;

            if(logicNull || boardNull || displayNull | snakeNull) {
                throwIncompleteBuildRuntimeException(logicNull, boardNull, displayNull, snakeNull);
            }

            if(newGame.directionQueued) {
                newGame.snake.setNextDirection(newGame.queuedDirection);
                newGame.directionQueued = false;
            }

            return newGame;

        }

        //No safety checks: used for testing
        public Game forceBuild() {
            return newGame;
        }

        private void throwIncompleteBuildRuntimeException(boolean logicNull, boolean boardNull, boolean displayNull, boolean snakeNull) {
            String exceptionString = "Error: Incomplete game build attempted: ";
            exceptionString += (logicNull) ? "gameLogic" : "";
            exceptionString += (logicNull && boardNull) ? "," : "";

            exceptionString += (boardNull) ? "gameBoard" : "";
            exceptionString += (boardNull && displayNull) ? "," : "";

            exceptionString += (displayNull) ? "gameDisplay" : "";
            exceptionString += (displayNull && snakeNull) ? "," : "";

            exceptionString += (snakeNull) ? "snake" : "";

            exceptionString += " was not assigned.";

            throw new RuntimeException(exceptionString);
        }
        public GameBuilder addGameBoard(int boardWidth, int boardHeight) {
            newGame.gameBoard = new GameBoard(boardWidth, boardHeight, newGame);
            return this;
        }

        public GameBuilder addStandardGameLogic() {
            newGame.gameLogic = new StandardGameLogic(newGame);
            return this;
        }

        public GameBuilder addSquareGameBoard(int boardSize) {
            newGame.gameBoard = new SquareGameBoard(boardSize, newGame);
            return this;
        }

        public GameBuilder addJavaXSwingDisplay() {
            newGame.gameDisplays.add(new JavaXSwingDisplay(newGame));
            return this;
        }

        public GameBuilder addStandardSnake() {
            newGame.snake = new Snake(newGame);
            return this;
        }

        public GameBuilder addRingGameBoard(int boardWidth, int boardHeight) {
            newGame.gameBoard = new RingGameBoard(boardWidth, boardHeight, newGame);
            return this;
        }

        public GameBuilder addObstacleDodgeLogic() {
            newGame.gameLogic = new ObstacleDodgeGameLogic(newGame);
            return this;
        }

        public GameBuilder addAcceleratingSnake() {
            newGame.snake = new AcceleratingSnake(newGame);
            return this;
        }

        public GameBuilder addMazeGameBoard(int boardWidth, int boardHeight, int tunnelWidth) {
            newGame.gameBoard = new MazeGameBoard(boardWidth, boardHeight, tunnelWidth, newGame);
            return this;
        }

        public GameBuilder addQuickGrowGameLogic() {
            newGame.gameLogic = new StandardGameLogic(newGame, 5);
            return this;
        }

        public Game createNewChaosGame() {
            newGame.gameLogic = new ChaosGameLogic(newGame);
            newGame.gameEngine = new TimerEngine(newGame);
            return build();
        }

        public GameBuilder addTimerEngine() {
            newGame.gameEngine = new TimerEngine(newGame);
            return this;
        }

        public GameBuilder addTimerEngine(int refreshRate) {
            newGame.gameEngine = new TimerEngine(newGame, refreshRate);
            return this;
        }

        public GameBuilder addConsoleEngine() {
            newGame.gameEngine = new ConsoleEngine(newGame);
            return this;
        }

        public GameBuilder addConsoleDisplay() {
            newGame.gameDisplays.add(new ConsoleDisplay(newGame));
            return this;
        }
    }
}
