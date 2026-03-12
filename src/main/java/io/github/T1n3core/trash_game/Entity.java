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
	
	public void shrinkHitbox(int shrinkWidth, int shrinkHeight) {
		int newWidth = hitbox.width - shrinkWidth;
		int newHeight = hitbox.height - shrinkHeight;
		
		hitbox.x += shrinkWidth / 2;
		hitbox.y += shrinkHeight / 2;
		hitbox.width = newWidth;
		hitbox.height = newHeight;
	}

	public void offsetHitbox(int offsetX, int offsetY) {
		hitbox.x += offsetX;
		hitbox.y += offsetY;
	}

	public abstract void update(GameState state);

	public abstract Team team();
}
