package Game;

import Game.Game.GameBuilder;

public class GameFactory {

    private final int STANDARD_BOARD_SIZE = 30;
    private final int TEXT_BASED_BOARD_SIZE = 30;

    public Game newStandardGame() {
        return new GameBuilder()
                .addStandardGameLogic()
                .addSquareGameBoard(30)
                .addStandardSnake()
                .addJavaXSwingDisplay()
                .build();
    }

}