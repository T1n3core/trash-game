package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;

public class Player extends Entity implements Movable, Shoots {
	private int movement;
	private static final int movementSpeed = 10;

	public Player(int x, int y, BufferedImage sprite) {
		super(x, y, sprite.getWidth(), sprite.getHeight(), sprite);
	}

	@Override
	public void update(GameState state) {
		handleInput(state);
		move();
		movement = 0;
	}

	@Override
	public void move() {
		setX(this.getX() + movement);
	}

	private void handleInput(GameState state) {
		if (state.moveLeft()) {
			movement -= movementSpeed;
		}

		if (state.moveRight()) {
			movement += movementSpeed;
		}
	}

	@Override
	public void shoot() {
		// TODO: implement shoot()
	}
}
