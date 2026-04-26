package GameDisplay;

import Game.*;
import Observers.IKeyObserver;
import Observers.IObserver;

import javax.swing.JFrame;

public class JavaXSwingDisplay extends JFrame implements IGameDisplay {

    private JavaXSwingPanel swingPanel;
    private Game game;

    @Override
    public void startDisplay() {
        swingPanel = new JavaXSwingPanel(game);
        add(swingPanel);
        revalidate();
        repaint();
        pack();
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
        setAlwaysOnTop(true);
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
        swingPanel.restart();
    }

    public JavaXSwingDisplay(Game game) {
        this.game = game;
        setTitle("Snake Game Plus");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
}