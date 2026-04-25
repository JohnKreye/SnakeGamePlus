package Snake;

import Game.Game;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class DecoratedSnake extends Snake {
    protected Snake innerSnake;

    public DecoratedSnake(Snake innerSnake) {
        this.innerSnake = innerSnake;
        super(innerSnake.game);
    }

    @Override
    public void restart() {
        innerSnake.restart();
    }

    @Override
    public void grow(int growthAmount) {
        innerSnake.grow(growthAmount);
    }

    @Override
    public boolean canGrow() {
        return innerSnake.canGrow();
    }

    @Override
    public List<Color> getColorPattern() {
        return innerSnake.getColorPattern();
    }

    @Override
    public boolean setNextDirection(Direction direction) {
        return innerSnake.setNextDirection(direction);
    }

    @Override
    public Direction getDirection() {
        return innerSnake.getDirection();
    }

    @Override
    public int getSize() {
        return innerSnake.getSize();
    }

    @Override
    public LinkedList<Point> getBodyPositions() {
        return innerSnake.getBodyPositions();
    }

    @Override
    public Point getHead() {
        return innerSnake.getHead();
    }

    @Override
    public void interactAction() {
        innerSnake.interactAction();
    }

    @Override
    public void move() {
        innerSnake.move();
    }
}
