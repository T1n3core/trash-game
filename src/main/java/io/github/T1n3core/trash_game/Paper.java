package io.github.T1n3core.trash_game;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Paper extends Enemy implements Shoots {
	public Paper(int x, int y) throws IOException {
		super(x, y, ImageIO.read(new File("paper.png")));
	}

	@Override
	public void shoot() {
		// TODO Implement shoot()
	}
}
