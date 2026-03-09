package io.github.T1n3core.trash_game;

public class GameState {
    private volatile boolean moveLeft;
    private volatile boolean moveRight;
    private volatile boolean shoot;

    public GameState() {
        this.moveLeft = false;
        this.moveRight = false;
        this.shoot = false;
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
}
