package Snake;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import Game.*;

public class Snake {

    public static enum Direction { UP, DOWN, LEFT, RIGHT }
    public static List<Color> colorPattern;

    private LinkedList<Point> body;
    private Direction currentDirection;
    private Direction nextDirection;
    private int growTicks;

    private Game game;

    public Snake(Game game) {
        body = new LinkedList<>();
        body.add(new Point(1,1));
        currentDirection = Direction.RIGHT;
        nextDirection = currentDirection;
        colorPattern = List.of(Color.green, new Color(0,150,0));
        growTicks = 2;
    }

    public void restart() {
        body = new LinkedList<>();
        body.add(new Point(1,1));
        currentDirection = Direction.RIGHT;
        nextDirection = currentDirection;
        growTicks = 2;

    }

    public List<Color> getColorPattern() { return colorPattern; }


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
        growTicks += growthAmount;
    }

    public void setNextDirection(Direction direction) {
        // Prevent 180-degree turns
        if ((this.currentDirection == Direction.UP && direction == Direction.DOWN) ||
                (this.currentDirection == Direction.DOWN && direction == Direction.UP) ||
                (this.currentDirection == Direction.LEFT && direction == Direction.RIGHT) ||
                (this.currentDirection == Direction.RIGHT && direction == Direction.LEFT)) {
            return;
        }
        this.nextDirection = direction;
    }

    public LinkedList<Point> getBodyPositions() {
        return body;
    }

    public Point getHead() {
        return body.getFirst();
    }
}