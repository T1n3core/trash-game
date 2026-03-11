package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;

public class Enemy extends Entity implements Movable {

	private int direction = 1; // 1 = right, -1 = left
	private int speed = 2;

	protected Enemy(int x, int y, BufferedImage sprite) {
		super(x, y, sprite.getWidth(), sprite.getHeight(), sprite);
	}

	@Override
	public void update(GameState state) {
		move();
	}

	@Override
	public void move() {
		
		int newX = getX() + direction * speed;

		// Screen bounds
		if (newX <= 0 || newX >= 800) {
			direction *= -1;
			setY(getY() + 40);
		} else {
			setX(newX);
		}
	}
}
