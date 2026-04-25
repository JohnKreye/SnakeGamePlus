package GameObject;

import Game.Game;

import java.awt.*;

public class GameObstacle extends GameObject {

    public GameObstacle(Point objectPosition) {
        Color objectColor = new Color(10, 10 ,10);
        super(objectColor, objectPosition);
    }

    @Override
    public void interact(Game game) {
        game.gameLogic.endGame(); //snake dies
    }
}
