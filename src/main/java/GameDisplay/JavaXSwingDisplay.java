package GameDisplay;

import Game.*;
import Observers.IKeyObserver;
import Observers.IObserver;

import javax.swing.JFrame;

public class JavaXSwingDisplay extends JFrame implements IGameDisplay, IDynamicSpeedDisplay {

    private static final int INITIAL_SPEED = 150; //Game Speed (ms)

    private JavaXSwingPanel swingPanel;
    private Game game;
    private boolean firstStart = true;

    @Override
    public void startDisplay() {
        if(firstStart) {
            swingPanel = new JavaXSwingPanel(game, INITIAL_SPEED);
            add(swingPanel);
            swingPanel.addKeyObserver((IKeyObserver) game.gameLogic);
            swingPanel.addObserver((IObserver) game.gameBoard);

            pack();
            setLocationRelativeTo(null); // Center on screen

            firstStart = false;
        }
        else {
            swingPanel.changeSpeed(INITIAL_SPEED);
        }

        this.setVisible(true);
    }

    @Override
    public void updateDisplay() {
        swingPanel.repaint();
    }

    @Override
    public void closeDisplay() {
        this.setVisible(false);
    }

    @Override
    public void changeSpeed(int speed) {
        swingPanel.changeSpeed(speed);
    }


    public JavaXSwingDisplay(Game game) {
        this.game = game;
        setTitle("Snake Game Plus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


    }
}