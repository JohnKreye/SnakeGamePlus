package GameEngine;

import Game.Game;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import javax.swing.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ConsoleEngine extends GameEngine {

    private Scanner inputScanner = new Scanner(System.in);
    private Timer consoleTimer;

    private List<String> AVAILABLE_USER_COMMANDS = List.of(
            "up", "down", "left", "right", "quit", "help"
    );

    public ConsoleEngine(Game game) {
        super(game);
    }

    @Override
    public void startGame() {
        super.startGame();
        updateDisplay();

        // Create a timer that runs every 100ms
        consoleTimer = new Timer(100, e -> {
            String action = getUserInput(); // This will now only run when data is ready
            processAction(action);

            if (game.gameLogic.gameIsOver()) {
                consoleTimer.stop();
            }
        });

        consoleTimer.start();
    }

    private void processAction(String action) {
        if (Objects.equals(action, "help")) {
            printUserCommands();
            return;
        }

        switch (action) {
            case "up":    game.gameLogic.keyPressed(NativeKeyEvent.VC_UP);    break;
            case "down":  game.gameLogic.keyPressed(NativeKeyEvent.VC_DOWN);  break;
            case "left":  game.gameLogic.keyPressed(NativeKeyEvent.VC_LEFT);  break;
            case "right": game.gameLogic.keyPressed(NativeKeyEvent.VC_RIGHT); break;
            case "quit": System.exit(0);
        }

        updateGame();
        updateDisplay();
    }

    private void printUserCommands() {
        System.out.println("User commands are as follows:");
        for(String command : AVAILABLE_USER_COMMANDS) {
            System.out.println("-" + command);
        }
    }

    private String getUserInput() {
        System.out.print("Select Command (\"help\" for list of commands)\n>");
        System.out.flush();
        String input = null;
        while (true) {
            input = inputScanner.nextLine();
            System.out.flush();
            input = input.toLowerCase();
            if(AVAILABLE_USER_COMMANDS.contains(input)) {
                break;
            }
            else {
                System.out.print(input + " is not a valid command. Type \"help\" for a list of commands. Input is not case sensitive.\n>");
                System.out.flush();
            }
        }
        return input;
    }
}
