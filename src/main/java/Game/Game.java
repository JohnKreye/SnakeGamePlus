package Game;

import GameBoard.*;
import GameDisplay.*;
import GameLogic.*;
import Snake.*;

public class Game {
    public GameLogic gameLogic;
    public GameBoard gameBoard;
    public IGameDisplay gameDisplay;
    public Snake snake;

    protected Game() {}

    public void playGame() {
        gameLogic.playGame();
    }

    public static class GameBuilder {
        private Game newGame;

        public GameBuilder() {
            this.newGame = new Game();
        }

        public Game build() {

            boolean logicNull = newGame.gameLogic == null;
            boolean boardNull = newGame.gameBoard == null;
            boolean displayNull = newGame.gameDisplay == null;
            boolean snakeNull = newGame.snake == null;

            if(logicNull || boardNull || displayNull | snakeNull) {
                throwIncompleteBuildRuntimeException(logicNull, boardNull, displayNull, snakeNull);
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

        public GameBuilder addGameLogic(GameLogic gameLogic) {
            newGame.gameLogic = gameLogic;
            return this;
        }

        public GameBuilder addGameBoard(GameBoard gameBoard) {
            newGame.gameBoard = gameBoard;
            return this;
        }

        public GameBuilder addGameDisplay(IGameDisplay IGameDisplay) {
            newGame.gameDisplay = IGameDisplay;
            return this;
        }

        public GameBuilder addSnake(Snake snake) {
            newGame.snake = snake;
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
            newGame.gameDisplay = new JavaXSwingDisplay(newGame);
            return this;
        }

        public GameBuilder addStandardSnake() {
            newGame.snake = new Snake(newGame);
            return this;
        }

        public GameBuilder addRingGameBoard(int boardWidth, int boardHeight) {
            newGame.gameBoard = new RingGameBoard(boardWidth, boardHeight, newGame);
            return  this;
        }
    }
}
