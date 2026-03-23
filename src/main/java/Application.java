import Game.*;
import javax.swing.*;
import java.util.List;

public class Application {

    private static List<String> GAME_MODES = List.of(
            "Standard"
    );

    private static String displayStringGameModes() {
        String output = "";
        for (String gameMode : GAME_MODES) {
            output = output + gameMode + "\n";
        }
        return output + "\n";
    }

    private static Game selectGameMode(String gameMode, GameFactory gameFactory) {
        switch (gameMode) {
            case "Standard":
                return gameFactory.newStandardGame();
            case null, default:
                throw new RuntimeException("Error: \"" + gameMode + "\" is not a valid gamemode. Valid gamemodes are as follows:\n" + displayStringGameModes());
        }
    }

    public static void main(String[] args) {

        int numberOfArguments = args.length;
        if (numberOfArguments > 1) {
            throw new RuntimeException("Error: SnakeGamePlus expected 0 to 1 arguments but recieved " + numberOfArguments + ".");
        }

        GameFactory gameFactory = new GameFactory();
        Game snakeGame;

        if (numberOfArguments == 0)
            snakeGame = gameFactory.newStandardGame();
        else
            snakeGame = selectGameMode(args[0], gameFactory);

        SwingUtilities.invokeLater(snakeGame::playGame);
    }
}
