package GameDisplay;

import Game.Game;
import GameObject.GameObject;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleDisplay implements IGameDisplay {
    //Color reset for ANSI
    public static final String ANSI_RESET = "\u001B[0m";
    private Game game;
    private int displayWidth;
    private int displayHeight;
    private Map<Point, Color> colorMap = new HashMap<>();

    private String lastPrint = "";

    public ConsoleDisplay(Game game) {
        this.game = game;
        this.displayWidth = game.gameBoard.getWidth();
        this.displayHeight = game.gameBoard.getHeight();
    }

    @Override
    public void startDisplay() {
        updateDisplay();
    }

    @Override
    public void updateDisplay() {
        if(!game.gameLogic.gameIsOver() && !game.gameLogic.gameIsPaused()) {
            lastPrint = "";
            System.out.println("Score : " + game.gameLogic.getScore());
            addBackGroundTiles();
            addGameObjects();
            addSnake();
            drawGame();
            System.out.println();
        }
        else {
            if(game.gameLogic.gameIsOver()) {
                smartPrint("\n\n\n===GAME OVER===\n\n\n");
            }
            else {
                smartPrint("\n\n\n===GAME PAUSED===\n\n\n");
            }
        }
    }

    private void smartPrint(String output) {
        if(!output.equals(lastPrint)) {
            System.out.print(output);
            lastPrint = output;
        }
    }

    @Override
    public void closeDisplay() {
        clearConsole();
    }

    @Override
    public void restartDisplay() {
        clearConsole();
        updateDisplay();
    }

    private static void clearConsole() {
        //"clear" console
        System.out.println("\n".repeat(100));
    }

    public static String toAnsiBackground(Color color) {
        return String.format("\u001B[48;2;%d;%d;%dm",
                color.getRed(), color.getGreen(), color.getBlue());
    }

    private void addBackGroundTiles() {
        List<Point> validPoints = game.gameBoard.getValidPositions();
        int size = validPoints.size();
        int index = 0;
        for(; index < size - 5; index += 5) {
            addBackGroundTile(validPoints.get(index + 0));
            addBackGroundTile(validPoints.get(index + 1));
            addBackGroundTile(validPoints.get(index + 2));
            addBackGroundTile(validPoints.get(index + 3));
            addBackGroundTile(validPoints.get(index + 4));
        }
        for(; index < size; index ++) {
            addBackGroundTile(validPoints.get(index));
        }
    }

    private void addBackGroundTile(Point point) {
        //even summed coordinates will have different color from odd summed coordinates
        //in other words a checkered pattern
        Color color1 = Color.DARK_GRAY.darker();
        Color color2 = color1.darker();
        int coordinateSum = point.x + point.y;

        if(coordinateSum % 2 == 0)
            addTile(point, color1);
        else
            addTile(point, color2);
    }

    private void addTile(Point point, Color color) {
        colorMap.put(point, color);
    }

    private void addSnake() {
        List<Color> snakeColorPattern = game.snake.getColorPattern();
        int totalColors = snakeColorPattern.size();
        int colorIndex = 0;
        for (Point bodyPartPosition : game.snake.getBodyPositions()) {
            addTile(bodyPartPosition, snakeColorPattern.get(colorIndex));
            colorIndex ++;

            if (colorIndex >= totalColors) {
                colorIndex = 0;
            }
        }
    }

    private void addGameObjects() {
        for (GameObject gameObject : game.gameBoard.getGameObjects()) {
            Point objectPosition = gameObject.getPosition();
            addTile(objectPosition, gameObject.getColor());
        }
    }

    private void drawGame() {
        for (int y = 0; y < displayHeight; y++) {
            for (int x = 0; x < displayWidth; x++) {
                Point tilePoint = new Point(x, y);
                Color tileColor = colorMap.getOrDefault(tilePoint, Color.BLACK);
                drawTile(tileColor);
            }
            System.out.println(); // Move to next row
        }
    }

    private void drawTile(Color color) {
        System.out.print(toAnsiBackground(color) + "   " + ANSI_RESET);
    }
}
