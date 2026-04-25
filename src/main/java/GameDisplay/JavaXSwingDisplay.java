package GameDisplay;

import Game.*;
import Observers.IKeyObserver;
import Observers.IObserver;

import javax.swing.JFrame;

public class JavaXSwingDisplay extends JFrame implements IGameDisplay, IDynamicSpeedDisplay {

    private static final int INITIAL_SPEED = 150; //Game Speed (ms)
    private int speed = INITIAL_SPEED;

    private JavaXSwingPanel swingPanel;
    private Game game;

    @Override
    public void startDisplay() {
        swingPanel = new JavaXSwingPanel(game, INITIAL_SPEED);
        add(swingPanel);
        swingPanel.addKeyObserver((IKeyObserver) game.gameLogic);
        swingPanel.addObserver((IObserver) game.gameBoard);
        this.revalidate();
        this.repaint();

        pack();
        setLocationRelativeTo(null); // Center on screen

        this.setVisible(true);
    }

    @Override
    public void updateDisplay() {
        swingPanel.repaint();
    }

    @Override
    public void closeDisplay() {
        swingPanel.close();
        remove(swingPanel);
        this.dispose();
    }

    @Override
    public void restartDisplay() {
        changeSpeed(INITIAL_SPEED);
        swingPanel.restart();
    }

    @Override
    public void changeSpeed(int speed) {
        swingPanel.changeSpeed(speed);
        this.speed = speed;
    }

    @Override
    public int getSpeed() {
        return speed;
    }


    public JavaXSwingDisplay(Game game) {
        this.game = game;
        setTitle("Snake Game Plus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
}