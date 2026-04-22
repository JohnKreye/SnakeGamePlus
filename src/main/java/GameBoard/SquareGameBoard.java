package GameBoard;
import Game.*;

import java.awt.*;

public class SquareGameBoard extends GameBoard {
    public SquareGameBoard(int boardSize, Game game) {
        super(boardSize, boardSize, game);
    }
}
