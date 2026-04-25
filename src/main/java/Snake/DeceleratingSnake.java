package Snake;

import Game.Game;

import java.awt.*;
import java.util.List;

public class DeceleratingSnake extends AcceleratingSnake {
    public DeceleratingSnake(Game game) {
        super(game);
        colorPattern = List.of(Color.red.darker(), Color.red.darker().darker());
        SPEED_MULTIPLIER = 1.05;
        SPEED_CEILING = 300;
    }
}
