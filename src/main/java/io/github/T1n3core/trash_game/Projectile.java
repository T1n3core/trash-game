package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;

public class Projectile extends Entity implements Movable {
	private Entity owner;
	private static final int movementSpeed = 25;

	public Projectile(Entity owner, int x, int y, BufferedImage sprite) {
		super(x, y, sprite.getWidth(), sprite.getHeight(), sprite);
		this.owner = owner;
	}

	@Override
	public void update(GameState state) {
		move();
		checkCollisions(state);
	}

	@Override
	public void move() {
		if (owner instanceof Player) {
			setY(getY() - movementSpeed);
		} else {
			setY(getY() + movementSpeed);
		}
	}

	private void checkCollisions(GameState state) {
		for (Entity e : state.getEntities()) {
			if (e.getClass().equals(owner.getClass()))
				continue;

			if (getHitbox().intersects(e.getHitbox())) {
				if (e instanceof Shield s) {
					s.damage();
				} else {
					state.kill(e);
				}
				state.kill(this);
			}
		}
	}
}
