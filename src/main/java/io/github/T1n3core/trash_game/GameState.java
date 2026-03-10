package io.github.T1n3core.trash_game;

import java.util.List;
import java.util.ArrayList;

public class GameState {
	private volatile boolean moveLeft;
	private volatile boolean moveRight;
	private volatile boolean shoot;
	private final List<Entity> entities;

	public GameState() {
		this.moveLeft = false;
		this.moveRight = false;
		this.shoot = false;
		this.entities = new ArrayList<>();
	}

	public void setMoveLeft(boolean moveLeft) {
		this.moveLeft = moveLeft;
	}

	public void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}

	public void setShoot(boolean shoot) {
		this.shoot = shoot;
	}

	public boolean moveLeft() {
		return moveLeft;
	}

	public boolean moveRight() {
		return moveRight;
	}

	public boolean shoot() {
		return shoot;
	}

	public void kill(Entity e) {
		entities.remove(e);
	}

	public List<Entity> getEntities() {
		return List.copyOf(entities);
	}
}
