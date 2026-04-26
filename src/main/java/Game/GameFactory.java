package Game;

import Game.Game.GameBuilder;

public class GameFactory {

    public Game newStandardGame() {
        return new GameBuilder()
                .addStandardGameLogic()
                .addSquareGameBoard(30)
                .addStandardSnake()
                .addJavaXSwingDisplay()
                .addTimerEngine()
                .build();
    }

    public Game newRampUpGame() {
        return new GameBuilder()
                .addObstacleDodgeLogic()
                .addRingGameBoard(45, 35)
                .addAcceleratingSnake()
                .addJavaXSwingDisplay()
                .addTimerEngine()
                .build();
    }

    public Game newMazeCrawlerGame() {
        return new GameBuilder()
                .addQuickGrowGameLogic()
                .addMazeGameBoard(19,19,3)
                .addStandardSnake()
                .addJavaXSwingDisplay()
                .addTimerEngine()
                .build();
    }

    public Game newChaosGame() {
        return new GameBuilder().createNewChaosGame();
    }

    public Game newConsoleControlled() {
        return new GameBuilder()
                .addStandardGameLogic()
                .addGameBoard(10,10)
                .addStandardSnake()
                .addJavaXSwingDisplay()
                .addConsoleEngine()
                .build();
    }

    public Game newFullyConsoleBased() {
        return new GameBuilder()
                .addStandardGameLogic()
                .addGameBoard(10, 10)
                .addStandardSnake()
                .addConsoleDisplay()
                .addConsoleEngine()
                .build();
    }

    public Game newTimerWithConsoleDisplay() {
        return new GameBuilder()
                .addStandardGameLogic()
                .addGameBoard(10, 10)
                .addStandardSnake()
                .addConsoleDisplay()
                .addTimerEngine(400)
                .build();
    }

}