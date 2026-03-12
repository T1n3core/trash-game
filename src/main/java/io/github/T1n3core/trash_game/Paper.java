package io.github.T1n3core.trash_game;

public class Paper extends Enemy implements Shoots {
	private int firingCooldown;

	public Paper(int x, int y) {
		super(x, y, ResourceCache.PAPER);
		firingCooldown = 90;
	}

	@Override
	public void shoot(GameState state) {
		if (firingCooldown != 0) {
			firingCooldown--;
			return;
		}

		if (Math.random() > 0.02) {
			firingCooldown = 15;
			return;
		}

		int projectileX = getX() + getHitbox().width / 2 - 40;
		int projectileY = getY() + getHitbox().height;

		Projectile projectile = new Projectile(this, projectileX, projectileY, ResourceCache.PLANE);
		projectile.shrinkHitbox(40, 20);
		state.spawn(projectile);

		firingCooldown = 150;
	}
}