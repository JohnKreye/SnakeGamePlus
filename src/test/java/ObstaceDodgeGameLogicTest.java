import Game.Game;
import Game.Game.GameBuilder;
import GameObject.GameObstacle;
import org.junit.jupiter.api.Test;

public class ObstaceDodgeGameLogicTest {

    @Test
    public void createObstacleOnScoreTest() {
        Game manualGame = new GameBuilder()
                .addObstacleDodgeLogic()
                .addStandardSnake() //used in obstacle placement calculation
                .addSquareGameBoard(30)
                .forceBuild();

        manualGame.gameLogic.addScore(10);

        assert gameBoardHasAnObstacle(manualGame);
    }

    private static boolean gameBoardHasAnObstacle(Game manualGame) {
        return !manualGame.gameBoard.getGameObjects().stream()
                .filter(gameObject -> gameObject instanceof GameObstacle)
                .toList()
                .isEmpty();
    }
}
