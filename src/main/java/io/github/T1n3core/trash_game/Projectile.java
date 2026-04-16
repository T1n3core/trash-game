package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;

public class Projectile extends Entity implements Movable {
	private static final int movementSpeed = 15;
	private final Team team;

	public Projectile(Entity owner, int x, int y, BufferedImage sprite) {
		super(x, y, sprite.getWidth(), sprite.getHeight(), sprite);
		this.team = owner.team();
	}

	@Override
	public void update(GameState state) {
		move(state);
		checkCollisions(state);
		if (getY() < 0 || getY() > GameConfig.SCREEN_HEIGHT) {
			state.kill(this);
		}
	}

	@Override
	public void move(GameState state) {
		if (team == Team.FRIENDLY) {
			setY(getY() - movementSpeed);
		} else {
			setY(getY() + movementSpeed);
		}
	}

	private void checkCollisions(GameState state) {
		for (Entity e : state.getEntities()) {
			if (e.team() == team) {
				continue;
			}

			if (getHitbox().intersects(e.getHitbox())) {
				if (e instanceof Shield s) {
					s.damage();
				} else if (e instanceof Player p) {
					p.damage(state);
				} else {
					state.kill(e);
				}
				state.kill(this);
			}
		}
	}

	@Override
	public Team team() {
		return team;
	}
}
