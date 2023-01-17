package gr.hua.dit.oop2.finaljukebox;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CustomKeyListener implements KeyListener {				//2nd Listener activated with Button click.

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        FrameStart fs = new FrameStart();

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            fs.functionListener();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
