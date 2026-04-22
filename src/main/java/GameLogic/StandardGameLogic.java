package GameLogic;
import Game.*;
import GameObject.*;

public class StandardGameLogic extends GameLogic {

    public StandardGameLogic(Game game) {
        super(game);
    }

    @Override
    public void playGame() {
        game.gameBoard.addGameObject(new StandardFood(game.gameBoard.getRandomValidPosition()));
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
        game.gameBoard.addGameObject(new StandardFood(game.gameBoard.getRandomValidPosition()));
    }

}
