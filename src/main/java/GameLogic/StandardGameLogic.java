package GameLogic;
import Game.*;
import GameObject.*;

public class StandardGameLogic extends GameLogic {
    private static int STANDARD_FOOD_GROWTH_AMOUNT = 1;
    private int foodGrowthAmount;

    public StandardGameLogic(Game game) {
        this(game, STANDARD_FOOD_GROWTH_AMOUNT);
    }

    public StandardGameLogic(Game game, int foodGrowthAmount) {
        this.foodGrowthAmount = foodGrowthAmount;
        super(game);
    }

    @Override
    public void playGame() {
        game.gameBoard.addGameObject(new StandardFood(game.gameBoard.getRandomFreePosition()));
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
    }

    @Override
    protected void addScoreAction() {
        game.gameBoard.addGameObject(new StandardFood(game.gameBoard.getRandomFreePosition()));
    }

}
