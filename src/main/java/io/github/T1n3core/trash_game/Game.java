package io.github.T1n3core.trash_game;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import javax.swing.JFrame;

public class Game extends JFrame {
	public Game() throws IOException {
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		GamePanel panel = new GamePanel();
		add(panel);

		setIconImage(ResourceCache.ICON);
		setTitle("Trash Game");

		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		device.setFullScreenWindow(this);

		panel.requestFocusInWindow();
	}
}
