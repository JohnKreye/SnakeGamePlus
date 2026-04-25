package Snake;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import Game.*;

public class Snake {

    public static enum Direction { UP, DOWN, LEFT, RIGHT }
    public static List<Color> colorPattern;

    protected LinkedList<Point> body;
    protected Direction currentDirection;
    protected Direction nextDirection;
    protected int growTicks;
    protected int DEFAULT_SIZE = 2;

    protected Game game;

    public Snake(Game game) {
        this.game = game;
        body = new LinkedList<>();
        body.add(new Point(1,1));
        currentDirection = Direction.RIGHT;
        nextDirection = currentDirection;
        colorPattern = List.of(Color.green, new Color(0,150,0));
        growTicks = DEFAULT_SIZE;
    }

    public void restart() {
        body = new LinkedList<>();
        body.add(new Point(1,1));
        currentDirection = Direction.RIGHT;
        nextDirection = currentDirection;
        growTicks = DEFAULT_SIZE;

    }

    public void move() {
        Point head = body.getFirst();
        Point newHead = new Point(head);

        switch (nextDirection) {
            case UP -> newHead.y--;
            case DOWN -> newHead.y++;
            case LEFT -> newHead.x--;
            case RIGHT -> newHead.x++;
        }
        currentDirection = nextDirection;

        body.addFirst(newHead);
        if (growTicks > 0) {
            growTicks--;
        }
        else {
            body.removeLast();
        }
    }

    public void grow(int growthAmount) {
        if(canGrow()) {
            growTicks += growthAmount;
        }
    }

    public boolean setNextDirection(Direction direction) {
        // Prevent 180-degree turns
        if ((this.currentDirection == Direction.UP && direction == Direction.DOWN) ||
                (this.currentDirection == Direction.DOWN && direction == Direction.UP) ||
                (this.currentDirection == Direction.LEFT && direction == Direction.RIGHT) ||
                (this.currentDirection == Direction.RIGHT && direction == Direction.LEFT)) {
            return false;
        }
        this.nextDirection = direction;
        return true;
    }

    public List<Color> getColorPattern() { return colorPattern; }
    public Direction getDirection() { return currentDirection; }
    public LinkedList<Point> getBodyPositions() { return body; }
    public Point getHead() { return body.getFirst(); }
    public int getSize() { return body.size() + growTicks; }

    public void interactAction() {} //needed for snakes that have special interaction actions
    public boolean canGrow() { return true;}



}