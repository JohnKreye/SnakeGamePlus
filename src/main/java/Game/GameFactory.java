package Game;

import Game.Game.GameBuilder;
import GameLogic.*;
import GameBoard.*;
import GameDisplay.*;

public class GameFactory {

    public Game newStandardGame() {
        int STANDARD_BOARD_SIZE = 30;

        return new GameBuilder()
                .addStandardGameLogic()
                .addSquareGameBoard(30)
                .addJavaXSwingDisplay()
                .addStandardSnake()
                .build();
    }

}
