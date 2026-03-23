package GameObject;

import Game.Game;
import Snake.Snake;

import java.awt.*;

public abstract class ConsumeableObject extends GameObject {
    protected ConsumeableObject(Color objectColor, Point objectPosition) {
        super(objectColor, objectPosition);
    }

    public void interact(Game game) {
        applyEffectTo(game.snake);
        game.gameLogic.removeConsumableObject(this);
    }

    public abstract void applyEffectTo(Snake snake);
}
