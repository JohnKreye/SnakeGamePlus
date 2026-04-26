package GameEngine;

import Game.Game;

import javax.swing.*;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class TimerEngine extends GameEngine implements NativeKeyListener {
    private static int DEFUALT_SPEED = 150;  //ms
    private int initialSpeed;
    private int speed;
    private Timer timer;

    public TimerEngine(Game game) {
        this(game, DEFUALT_SPEED);
    }

    public TimerEngine(Game game, int speed) {
        super(game);
        this.initialSpeed = speed;
    }

    @Override
    public void startGame() {
        super.startGame();
        setSpeed(initialSpeed);

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            ex.printStackTrace();
        }
    }


    @Override
    public void setSpeed(int refreshSpeed) {
//        System.out.println("Requested Speed: " + refreshSpeed);
        speed = refreshSpeed;

        if (timer == null) {
            timer = new Timer(refreshSpeed, e -> {
                updateGame();
                updateDisplay();
            });
            timer.start();
        }
        else {
            timer.setDelay(refreshSpeed);
            timer.setInitialDelay(0);
            timer.restart();
//            System.out.println("Timer Delay is now: " + timer.getDelay());
        }
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
//        System.out.println("Key Pressed : " + NativeKeyEvent.getKeyText(nativeEvent.getKeyCode()));
        game.gameLogic.keyPressed(nativeEvent.getKeyCode());
    }
}
