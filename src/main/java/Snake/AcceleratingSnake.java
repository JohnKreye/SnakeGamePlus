package Snake;

import Game.Game;
import GameDisplay.IDynamicSpeedDisplay;

import java.awt.*;
import java.util.List;

public class AcceleratingSnake extends Snake {
    protected double SPEED_MULTIPLIER = 0.95;
    protected int SPEED_FLOOR = 40; //ms per frame
    protected int SPEED_CEILING = 150;

    public AcceleratingSnake(Game game) {
        super(game);
        colorPattern = List.of(Color.blue, Color.blue.darker());
        DEFAULT_SIZE = 5;
        growTicks = DEFAULT_SIZE;
    }

    @Override
    public void interactAction() {
        //This snake can only be used with dynamic speed displays so this conversion is safe
        IDynamicSpeedDisplay dynamicSpeedDisplay = (IDynamicSpeedDisplay) game.gameDisplay;
        int previousSpeed= dynamicSpeedDisplay.getSpeed();
        int newSpeed = Math.max((int)(previousSpeed * SPEED_MULTIPLIER), SPEED_FLOOR);
        newSpeed = Math.min(newSpeed, SPEED_CEILING);

        dynamicSpeedDisplay.changeSpeed(newSpeed);
    }

    @Override
    public boolean canGrow() {
        return false;
    }
}
