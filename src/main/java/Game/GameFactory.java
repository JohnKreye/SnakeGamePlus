package Game;

import Game.Game.GameBuilder;

public class GameFactory {

    public Game newStandardGame() {
        return new GameBuilder()
                .addStandardGameLogic()
                .addSquareGameBoard(30)
                .addStandardSnake()
                .addJavaXSwingDisplay()
                .build();
    }

    public Game newRampUpGame() {
        return new GameBuilder()
                .addObstacleDodgeLogic()
                .addRingGameBoard(45, 35)
                .addAcceleratingSnake()
                .addJavaXSwingDisplay()
                .build();
    }

    public Game newMazeCrawlerGame() {
        return new GameBuilder()
                .addQuickGrowGameLogic()
                .addMazeGameBoard(19,19,3)
                .addStandardSnake()
                .addJavaXSwingDisplay()
                .build();
    }

    public Game newChaosGame() {
        return new GameBuilder().createNewChaosGame();
    }
}