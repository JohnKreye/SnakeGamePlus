package GameBoard;

import Game.Game;
import Snake.Snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MazeGameBoard extends GameBoard {
    private int tunnelWidth;
    private Snake.Direction firstDirection;

    public MazeGameBoard(int boardWidth, int boardHeight, int tunnelWidth, Game game) {
        this.tunnelWidth = tunnelWidth;
        super(boardWidth, boardHeight, game);
    }

    @Override
    protected void constructGameBoard(int boardWidth, int boardHeight) {
        //First, set all map points to invalid, so that the maze can be carved.
        for(int row = 0; row < boardHeight; row++) {
            int column = 0;
            for(; column < boardWidth - 5; column += 5) {
                validPointMap.put(new Point(column + 0, row), false);
                validPointMap.put(new Point(column + 1, row), false);
                validPointMap.put(new Point(column + 2, row), false);
                validPointMap.put(new Point(column + 3, row), false);
                validPointMap.put(new Point(column + 4, row), false);
            }
            for(; column < boardWidth; column++) {
                validPointMap.put(new Point(column, row), false);
            }
        }

        //maze construction
        int step = tunnelWidth + 1; // Gap between logical cell starts
        Stack<Point> stack = new Stack<>();
        List<Point> visited = new ArrayList<>();

        Point start = new Point(0, 0);
        stack.push(start);
        visited.add(start);
        carveSquareFromCorner(start.x, start.y);

        while (!stack.isEmpty()) {
            Point curr = stack.peek();
            List<Point> neighbors = getUnvisitedNeighbors(curr, visited, step);

            if (!neighbors.isEmpty()) {
                int size = neighbors.size();
                Point next = neighbors.get(random.nextInt(neighbors.size()));

                //save first direction that the maze carves into
                if (firstDirection == null) {
                    calculateFirstDirection(next, curr, step);
                }

                // Carve the tunnel between cells and the target cell itself
                carveTunnel(curr, next);
                carveSquareFromCorner(next.x, next.y);

                visited.add(next);
                stack.push(next);
            } else {
                stack.pop();
            }
        }

        //Prevents snake from crashing into wall
        if(game.snake == null) {
            game.queueSnakeDirectionChange(firstDirection);
        }
        else {
            game.snake.setNextDirection(firstDirection);
        }

    }

    private void calculateFirstDirection(Point next, Point curr, int step) {
        // This creates a normalized vector (e.g., 0,1 or 1,0)
        int[] directionVector = {
            (next.x - curr.x)/ step,
            (next.y - curr.y)/ step
        };

        switch (directionVector[0]) {
            case 0:
                switch (directionVector[1]) {
                    case 1:
                        firstDirection = Snake.Direction.DOWN;
                        break;
                    case -1:
                        firstDirection = Snake.Direction.UP;
                        break;
                }
                break;
            case 1:
                firstDirection = Snake.Direction.RIGHT;
                break;
            case -1:
                firstDirection = Snake.Direction.LEFT;
                break;
        }
    }

    private void carveSquareFromCorner(int cornerX, int cornerY) {
        for (int currentPointX = 0; currentPointX < tunnelWidth; currentPointX++) {
            for (int currentPointY = 0; currentPointY < tunnelWidth; currentPointY++) {
                if (cornerX + currentPointX < boardWidth && cornerY + currentPointY < boardHeight)
                    validPointMap.put(new Point(cornerX + currentPointX, cornerY + currentPointY), true);
            }
        }
    }

    private void carveTunnel(Point point1, Point point2) {
        int startX = Math.min(point1.x, point2.x);
        int startY = Math.min(point1.y, point2.y);
        if (point1.x != point2.x) { // Horizontal tunnel
            for (int currentY = 0; currentY < tunnelWidth; currentY++)
                validPointMap.put(new Point(startX + tunnelWidth, startY + currentY), true);
        } else { // Vertical tunnel
            for (int currentX = 0; currentX < tunnelWidth; currentX++)
                validPointMap.put(new Point(startX + currentX, startY + tunnelWidth), true);
        }
    }

    private List<Point> getUnvisitedNeighbors(Point point, List<Point> visited, int step) {
        List<Point> neighbors = new ArrayList<>();
        int[][] directions = {{0, step}, {0, -step}, {step, 0}, {-step, 0}};
        for (int[] direction : directions) {
            Point next = new Point(point.x + direction[0], point.y + direction[1]);
            if (next.x >= 0 && next.x <= boardWidth - tunnelWidth &&
                    next.y >= 0 && next.y <= boardHeight - tunnelWidth && !visited.contains(next)) {
                neighbors.add(next);
            }
        }
        return neighbors;
    }

    @Override
    public void restart() {
        validPointMap.clear();
        firstDirection = null;
        constructGameBoard(boardWidth, boardHeight);
    }
}
