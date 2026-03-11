package io.github.T1n3core.trash_game;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import javax.swing.JFrame;

public class Game extends JFrame {
    public Game() throws IOException {
        setUndecorated(true);
        setIgnoreRepaint(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        device.setFullScreenWindow(this);

        add(new GamePanel());
    }
}
