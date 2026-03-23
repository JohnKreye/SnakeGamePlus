package GameLogic;
import Game.*;
import GameObject.GameObject;
import GameObject.*;

import java.awt.*;
import java.util.Random;

public class StandardGameLogic extends GameLogic {

    public StandardGameLogic(Game game) {
        super(game);
    }

    @Override
    public void playGame() {
        game.gameBoard.addGameObject(new StandardFood(getRandomPosition()));
        super.playGame();
    }

    @Override
    public void restart() {
        game.gameBoard.clearGameObjects();
        super.restart();
    }

    @Override
    public void removeConsumableObject(ConsumeableObject consumeableObject) {
        super.removeConsumableObject(consumeableObject);
        game.gameBoard.addGameObject(new StandardFood(getRandomPosition()));
    }

}
