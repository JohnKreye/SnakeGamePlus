package GameObject;

import GameBoard.GameBoard;

import java.awt.*;
import Game.*;

public abstract class GameObject {

    private Color objectColor;
    private Point objectPosition;
    private GameBoard containingGameBoard;

    protected GameObject(Color objectColor, Point objectPosition) {
        this.objectColor = objectColor;
        this.objectPosition = objectPosition;
    }

    public abstract void interact(Game game);

    public Color getColor() { return objectColor; }
    public Point getPosition() { return objectPosition; }
}
