package GameBoard;
import Game.*;

import java.awt.*;

public class SquareGameBoard extends GameBoard {
    public SquareGameBoard(int boardSize, Game game) {
        super(boardSize, boardSize, game);

        for(int row = 0; row < boardSize; row++) {
            int column = 0;
            for(; column < boardSize; column += 5) {
                boardMap.put(new Point(row, column + 0), true);
                boardMap.put(new Point(row, column + 1), true);
                boardMap.put(new Point(row, column + 2), true);
                boardMap.put(new Point(row, column + 3), true);
                boardMap.put(new Point(row, column + 4), true);
            }
            column -= 5;
            for(; column < boardSize; column++) {
                boardMap.put(new Point(row, column), true);
            }
        }
    }
}
