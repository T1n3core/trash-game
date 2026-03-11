package io.github.T1n3core.trash_game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity {
	private int x;
	private int y;
	private final Rectangle hitbox;
	private final BufferedImage sprite;

	protected Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.hitbox = new Rectangle(x, y, width, height);
		this.sprite = sprite;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Rectangle getHitbox() {
		return new Rectangle(hitbox);
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setX(int x) {
		this.x = x;
		hitbox.x = x;
	}

	public void setY(int y) {
		this.y = y;
		hitbox.y = y;
	}

	public abstract void update(GameState state);
}
