package GameLogic;

import Game.Game;
import GameObject.ConsumeableObject;

import java.awt.event.KeyEvent;
import java.util.List;

public abstract class DecoratedGameLogic extends GameLogic {
    protected GameLogic innerGameLogic;

    public DecoratedGameLogic(Game game, GameLogic innerGameLogic) {
        super(game);
        this.innerGameLogic = innerGameLogic;
    }

    @Override
    public void playGame() {
        innerGameLogic.playGame();
    }

    @Override
    public void keyPressed(KeyEvent event) {
        innerGameLogic.keyPressed(event);
    }

    @Override
    protected void addScoreAction() {
        innerGameLogic.addScoreAction();
    }

    @Override
    public void checkCollisions() {
        innerGameLogic.checkCollisions();
    }

    @Override
    public void checkGameObjects() {
        innerGameLogic.checkGameObjects();
    }

    @Override
    protected void restart() {
        innerGameLogic.restart();
    }

    @Override
    public boolean gameIsOver() {
        return innerGameLogic.gameIsOver();
    }

    @Override
    public boolean gameIsPaused() {
        return innerGameLogic.gameIsPaused();
    }

    @Override
    public String getScore() {
        return innerGameLogic.getScore();
    }

    @Override
    public void addScore(int score) {
        innerGameLogic.addScore(score);
    }

    @Override
    public void endGame() {
        innerGameLogic.endGame();
    }

    @Override
    public void removeConsumableObject(ConsumeableObject consumeableObject) {
        innerGameLogic.removeConsumableObject(consumeableObject);
    }
}
