package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

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
		shoot(state);
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
	public void shoot(GameState state) {
		if (!state.shoot()) {
			return;
		}

		int projectileX = getX() + getHitbox().width / 2;
		int projectileY = getY();

		try {
			Projectile projectile = new Projectile(this, projectileX, projectileY, ImageIO.read(new File("player_projectile.png")));
			state.spawn(projectile);
		} catch (Exception e) {
			System.err.println("Could not load player projectile sprite, projectile will be invisible");
		}
	}
}