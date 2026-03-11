package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;

public class Enemy extends Entity implements Movable {
	private int direction = 1; // 1 = right, -1 = left
	private final int speed = 2;

	private static final int SCREEN_WIDTH = 800; // TODO might be worth making a game config class

	protected Enemy(int x, int y, BufferedImage sprite) {
		super(x, y, sprite.getWidth(), sprite.getHeight(), sprite);
	}

	@Override
	public void update(GameState state) {
		move();

		if (this instanceof Shoots shooter) {
			shooter.shoot(state);
		}
	}

	@Override
	public void move() { // TODO implement movement in formations	
		int newX = getX() + direction * speed;

		// temporary hardcode
		if (newX <= 0 || newX >= SCREEN_WIDTH) {
			direction *= -1;
			setY(getY() + 40);
		} else {
			setX(newX);
		}
	}
}
