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

    private int score;

    public GameState() {
        this.moveLeft = false;
        this.moveRight = false;
        this.shoot = false;
        this.entities = new ArrayList<>();
        this.spawnQueue = new ArrayList<>();
        this.killQueue = new ArrayList<>();
        this.score = 0;
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

        if (e instanceof Enemy) {
            score += 100;
        }

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
    }

	public int getScore() {
		return score;
	}
}
