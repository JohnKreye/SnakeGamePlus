package GameBoard;

import Game.Game;

import java.awt.*;

public class RingGameBoard extends GameBoard {

    public RingGameBoard(int boardWidth, int boardHeight, Game game) {
        super(boardWidth, boardHeight, game);
    }

    private void computePointValidness(Point point) {
        //A ring has a hole in it; we will make that hole into invalid points
        int minDimension = Math.min(boardHeight, boardWidth);
        int holeOffset = minDimension/5;

        if (
            (point.x > holeOffset && point.x < boardWidth - holeOffset) &&
            (point.y > holeOffset && point.y < boardHeight - holeOffset)
        ) {
            validPointMap.put(point, false);
        }
        else {
            validPointMap.put(point, true);
        }

    }

    @Override
    protected void constructGameBoard(int boardWidth, int boardHeight) {
        for(int row = 0; row < boardHeight; row++) {
            int column = 0;
            for(; column < boardWidth - 5; column += 5) {
                computePointValidness(new Point(row, column + 0));
                computePointValidness(new Point(row, column + 1));
                computePointValidness(new Point(row, column + 2));
                computePointValidness(new Point(row, column + 3));
                computePointValidness(new Point(row, column + 4));
            }
            column -= 5;
            for(; column < boardWidth; column++) {
                computePointValidness(new Point(row, column));
            }
        }
    }
}
