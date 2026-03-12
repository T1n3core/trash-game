package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;

public class Enemy extends Entity implements Movable {
	private static final Team team = Team.ENEMY;
	private int direction;
	private static final int SPEED = 2;
	private static final int DROP_DISTANCE = 40;

	protected Enemy(int x, int y, BufferedImage sprite) {
		super(x, y, sprite.getWidth(), sprite.getHeight(), sprite);
		direction = 1;
	}

	@Override
	public void update(GameState state) {
		move(state);

		if (this instanceof Shoots shooter) {
			shooter.shoot(state);
		}
	}

	@Override
	public void move(GameState state) {
		
		boolean shouldDrop = false;

		for (Entity e : state.getEntities()) {
			if (!(e instanceof Enemy enemy)) {
				continue;
			}

			int nextX = enemy.getX() + direction * SPEED;

			if (nextX <= 0 || nextX >= GameConfig.SCREEN_WIDTH - enemy.getHitbox().width) {
				shouldDrop = true;
				break;
			}
		}

		if (shouldDrop) {
			direction *= -1;

			for (Object e : state.getEntities()) {
				if (e instanceof Enemy enemy) {
					enemy.setY(enemy.getY() + DROP_DISTANCE);
				}
			}
		} else {
			setX(getX() + direction * SPEED);
		}
	}

	@Override
	public Team team() {
		return team;
	}
}
