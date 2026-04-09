package io.github.T1n3core.trash_game;

import java.util.ArrayList;
import java.util.List;

public class GameState {
	private volatile boolean moveLeft;
	private volatile boolean moveRight;
	private volatile boolean shoot;
	private final List<Entity> entities;
	private final List<Entity> spawnQueue;
	private final List<Entity> killQueue;
	private final List<ScorePopup> popupSpawnQueue;
	public static int score = 0;

	public GameState() {
		this.moveLeft = false;
		this.moveRight = false;
		this.shoot = false;
		this.entities = new ArrayList<>();
		this.spawnQueue = new ArrayList<>();
		this.killQueue = new ArrayList<>();
		this.popupSpawnQueue = new ArrayList<>();
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
		int points = 0;

		switch (e) {
			case Plastic _ -> points = 100;
			case Paper _ -> points = 200;
			case Bio _ -> points = 300;
			default -> points = 0;
		}

		popupSpawnQueue.add(new ScorePopup(e.getX() + e.getHitbox().width / 2, e.getY(), points));
		score += points;
		killQueue.add(e);
	}

	public void spawn(Entity e) {
		spawnQueue.add(e);
	}

	public List<Entity> getEntities() {
		return List.copyOf(entities);
	}

	public void commit() {
		entities.removeAll(killQueue);
		entities.addAll(spawnQueue);

		killQueue.clear();
		spawnQueue.clear();
		popupSpawnQueue.clear();
	}

	public int getScore() {
		return score;
	}

	public List<ScorePopup> getPopupSpawnQueue() {
		return List.copyOf(popupSpawnQueue);
	}
}
