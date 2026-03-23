package GameObject;

import Game.Game;
import Snake.Snake;

import java.awt.*;

public class StandardFood extends ConsumeableObject{

    public StandardFood(Point objectPosition) {
        Color objectColor = Color.red;
        super(objectColor, objectPosition);
    }

    @Override
    public void interact(Game game) {
        game.gameLogic.addScore(10);
        super.interact(game);
    }

    @Override
    public void applyEffectTo(Snake snake) {
        snake.grow(1);
    }
}
