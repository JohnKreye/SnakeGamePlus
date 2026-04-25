package GameObject;

import Game.Game;
import Snake.Snake;

import java.awt.*;

public class StandardFood extends ConsumeableObject{
    private int growthAmount;

    public StandardFood(Point objectPosition) {
        this(objectPosition, 1);
    }

    public StandardFood(Point objectPosition, int growthAmount) {
        Color objectColor = Color.red;
        this.growthAmount = growthAmount;
        super(objectColor, objectPosition);
    }

    @Override
    public void interact(Game game) {
        super.interact(game);
        game.gameLogic.addScore(10);
    }

    @Override
    public void applyEffectTo(Snake snake) {
        snake.grow(growthAmount);
    }
}
