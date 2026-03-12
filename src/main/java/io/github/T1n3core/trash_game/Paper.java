package io.github.T1n3core.trash_game;

public class Paper extends Enemy implements Shoots {
	private int firingCooldown;

	public Paper(int x, int y) {
		super(x, y, ResourceCache.PAPER);
		firingCooldown = 90;
	}

	@Override
	public void shoot(GameState state) {
		if (Math.random() > 0.02) {
			return;
		}

		if (firingCooldown != 0) {
			firingCooldown--;
			return;
		}

		int projectileX = getX() + getHitbox().width / 2 - 5; // Assuming a 10px projectile
		int projectileY = getY() + getHitbox().height;

		Projectile projectile = new Projectile(this, projectileX, projectileY, ResourceCache.PLANE);
		state.spawn(projectile);

		firingCooldown = 150;
	}
}