import Game.Game;
import Game.Game.GameBuilder;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;


public class RingBoardTest {

    @Test
    public void ringConstructionTest() {
        Game manualGame = new GameBuilder().addRingGameBoard(5, 5).forceBuild();

        List<Point> expectedValidPositions = List.of(
                new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0), new Point(4, 0),
                new Point(0, 1)                                                                     , new Point(4, 1),
                new Point(0, 2)                                                                     , new Point(4, 2),
                new Point(0, 3)                                                                     , new Point(4, 3),
                new Point(0, 4), new Point(1, 4), new Point(2, 4), new Point(3, 4), new Point(4, 4)
        );

        List<Point> actualValidPositions = manualGame.gameBoard.getValidPositions();

        System.out.println("Expected : " + expectedValidPositions);
        System.out.println("Actual : " + actualValidPositions);

        assert CollectionUtils.isEqualCollection(expectedValidPositions, actualValidPositions);
    }
}
