package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;

public class Enemy extends Entity implements Movable {
	private static final Team team = Team.ENEMY;

	protected Enemy(int x, int y, BufferedImage sprite) {
		super(x, y, sprite.getWidth(), sprite.getHeight(), sprite);
	}

	@Override
	public void update(GameState state) {
		move(state);

		checkGameOverCollision(state);

		if (this instanceof Shoots shooter) {
			shooter.shoot(state);
		}
	}

	@Override
	public void move(GameState state) {}

	@Override
	public Team team() {
		return team;
	}

	private void killPlayer(GameState state) {
		for (Entity e : state.getEntities()) {
			if (e instanceof Player) {
				state.kill(e);
			}
		}
	}

	private  void checkGameOverCollision(GameState state) {
		for (Entity e : state.getEntities()) {
			
			// Enemy touches shield
			if (e instanceof Shield && getHitbox().intersects(e.getHitbox())) {
				killPlayer(state);
				return;
			}

			// Enemy touches player
			if (e instanceof Player && getHitbox().intersects(e.getHitbox())) {
				state.kill(e);
				return;
			}
		}
	}
}
