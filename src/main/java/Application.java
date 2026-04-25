import Game.*;
import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class Application {
    private static Scanner inputScanner = new Scanner(System.in);
    private static GameFactory gameFactory = new GameFactory();
    private static List<String> GAME_MODES = List.of(
            "Standard",
            "Ramp up",
            "Maze crawler",
            "Chaos"
    );

    public static void main(String[] args) {

        printTitleText();

        int numberOfArguments = args.length;
        if (numberOfArguments > 0) {
            throw new RuntimeException("Error: SnakeGamePlus expected 0 arguments but recieved " + numberOfArguments + ".");
        }

        String gameModeString = getGameModeInput();
        Game game = selectGameMode(gameModeString);
        SwingUtilities.invokeLater(game::playGame);

    }

    private static void printTitleText() {
        System.out.println("""
                  ______                       __                         ______                                                   \s
                 /      \\                     |  \\                       /      \\                                             __   \s
                |  $$$$$$\\ _______    ______  | $$   __   ______        |  $$$$$$\\  ______   ______ ____    ______           |  \\  \s
                | $$___\\$$|       \\  |      \\ | $$  /  \\ /      \\       | $$ __\\$$ |      \\ |      \\    \\  /      \\        __| $$__\s
                 \\$$    \\ | $$$$$$$\\  \\$$$$$$\\| $$_/  $$|  $$$$$$\\      | $$|    \\  \\$$$$$$\\| $$$$$$\\$$$$\\|  $$$$$$\\      |    $$  \\
                 _\\$$$$$$\\| $$  | $$ /      $$| $$   $$ | $$    $$      | $$ \\$$$$ /      $$| $$ | $$ | $$| $$    $$       \\$$$$$$$$
                |  \\__| $$| $$  | $$|  $$$$$$$| $$$$$$\\ | $$$$$$$$      | $$__| $$|  $$$$$$$| $$ | $$ | $$| $$$$$$$$         | $$  \s
                 \\$$    $$| $$  | $$ \\$$    $$| $$  \\$$\\ \\$$     \\       \\$$    $$ \\$$    $$| $$ | $$ | $$ \\$$     \\          \\$$  \s
                  \\$$$$$$  \\$$   \\$$  \\$$$$$$$ \\$$   \\$$  \\$$$$$$$        \\$$$$$$   \\$$$$$$$ \\$$  \\$$  \\$$  \\$$$$$$$               \s
    
                WELCOME!
                """);
    }

    private static String displayStringGameModes() {
        String output = "";
        for (String gameMode : GAME_MODES) {
            output = output + gameMode + "\n";
        }
        //extra newline removal
        output = output.substring(0, output.length() - 1);
        return output;
    }

    private static Game selectGameMode(String gameMode) {
        switch (gameMode) {
            case "standard":
                return gameFactory.newStandardGame();
            case "ramp up":
                return gameFactory.newRampUpGame();
            case "maze crawler":
                return gameFactory.newMazeCrawlerGame();
            case "chaos":
                return gameFactory.newChaosGame();
            case null, default:
                throw new RuntimeException("Error: \"" + gameMode + "\" is not a registered game mode. Registered game modes are as follows:\n" + displayStringGameModes());
        }
    }

    private static String getGameModeInput() {
        System.out.print("Please select one of the following game modes:\n" + displayStringGameModes() + "\n>");
        String input = null;
        List<String> lowerCaseGameModes = GAME_MODES.stream().map(String::toLowerCase).toList();
        while (true) {
            input = inputScanner.nextLine();
            input = input.toLowerCase();
            if(lowerCaseGameModes.contains(input)) {
                break;
            }
            else {
                System.out.print(input + " is not a valid game mode. Please try again. Input is not case sensitive.\n>");
            }
        }
        return input;
    }
}
