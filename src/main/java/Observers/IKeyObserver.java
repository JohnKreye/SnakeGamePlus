package Observers;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import java.awt.event.KeyEvent;

public interface IKeyObserver {
    public void keyPressed(int keyCode);
}
