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
		move();

		if (this instanceof Shoots shooter) {
			shooter.shoot(state);
		}
	}

	@Override
	public void move() {
		int newX = getX() + direction * SPEED;

		if (newX <= 0 || newX >= GameConfig.SCREEN_WIDTH - getHitbox().width) {
			direction *= -1;
			setY(getY() + DROP_DISTANCE);
		} else {
			setX(newX);
		}
	}

	@Override
	public Team team() {
		return team;
	}
}
