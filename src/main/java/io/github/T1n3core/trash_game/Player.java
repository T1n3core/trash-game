package io.github.T1n3core.trash_game;

public class Player extends Entity implements Movable, Shoots {
	private int movement;
	private static final int movementSpeed = 10;
	public static final Team team = Team.FRIENDLY;

	public Player(int x, int y) {
		super(x, y, ResourceCache.PLAYER.getWidth(), ResourceCache.PLAYER.getHeight(), ResourceCache.PLAYER);
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
		int screenWidth = 800; // TODO move to config class
		int newX = Math.max(0, Math.min(getX() + movement, screenWidth - getHitbox().width));
		setX(newX);
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

		// TODO add a firing cooldown

		int projectileX = getX() + getHitbox().width / 2;
		int projectileY = getY();

		Projectile projectile = new Projectile(this, projectileX, projectileY, ResourceCache.PLAYER); // TODO add actual player projectile resource to the resources folder
		state.spawn(projectile);
	}

	@Override
	public Team team() {
		 return team;
	}
}