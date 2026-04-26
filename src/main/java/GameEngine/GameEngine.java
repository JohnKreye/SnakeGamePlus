package GameEngine;

import Game.Game;
import GameDisplay.IGameDisplay;

public abstract class GameEngine {
    protected Game game;

    public GameEngine(Game game) {
        this.game = game;
    }

    public void startGame() {
        for(IGameDisplay display : game.gameDisplays) {
            display.startDisplay();
        }
        this.game.gameLogic.playGame();
    }

    public void updateDisplay() {
        for(IGameDisplay display : game.gameDisplays) {
            display.updateDisplay();
        }
    }

    public void updateGame() {
        game.gameLogic.update();
    }

    //some engines don't have a refresh rate, so these don't do anything by default
    public void setSpeed(int refreshRate) {}
    public int getSpeed() { return 0; }
}
