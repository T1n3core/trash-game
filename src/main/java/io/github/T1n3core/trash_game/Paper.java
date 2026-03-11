package io.github.T1n3core.trash_game;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Paper extends Enemy implements Shoots {
	public Paper(int x, int y) throws IOException {
		super(x, y, ImageIO.read(new File("paper.png")));
	}

	@Override
	public void shoot(GameState state) {
		if (Math.random() > 0.02) {
			return;
		}

		int projectileX = getX() + getHitbox().width / 2 - 5; // Assuming a 10px projectile
		int projectileY = getY() + getHitbox().height;

		Projectile projectile = new Projectile(this, projectileX, projectileY, getSprite());

		state.spawn(projectile);
	}
}