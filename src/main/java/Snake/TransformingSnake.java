package Snake;

public class TransformingSnake extends DecoratedSnake {

    public TransformingSnake(Snake innerSnake) {
        super(innerSnake);
    }

    public void transFormInto(Snake newSnake) {
        newSnake.body = innerSnake.body;
        newSnake.growTicks = innerSnake.growTicks;
        newSnake.currentDirection = innerSnake.currentDirection;
        newSnake.nextDirection = innerSnake.nextDirection;
        innerSnake = newSnake;
    }
}
