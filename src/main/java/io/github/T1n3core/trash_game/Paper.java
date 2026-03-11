package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;

public class Paper extends Enemy implements Shoots {
	public Paper(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}

	@Override
	public void shoot(GameState state) {
		if (Math.random() > 0.02) {
			return;
		}

		int projectileX = getX() + getHitbox().width / 2;
		int projectileY = getY() + getHitbox().height;

		Projectile projectile = new Projectile(this, projectileX, projectileY, getSprite());

		state.spawn(projectile);
	}
}
