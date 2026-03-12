package io.github.T1n3core.trash_game;

public class Player extends Entity implements Movable, Shoots {
	private int movement;
	private int firingCooldown;
	private static final int MOVEMENT_SPEED = 10;
	public static final Team team = Team.FRIENDLY;

	public Player(int x, int y) {
		super(x, y, ResourceCache.PLAYER.getWidth(), ResourceCache.PLAYER.getHeight(), ResourceCache.PLAYER);
		movement = 0;
		firingCooldown = 0;
	}

	@Override
	public void update(GameState state) {
		handleInput(state);
		move(state);
		shoot(state);
		movement = 0;
		firingCooldown--;
	}

	@Override
	public void move(GameState state) {
		int newX = Math.max(0, Math.min(getX() + movement, GameConfig.SCREEN_WIDTH - getHitbox().width));
		setX(newX);
	}

	private void handleInput(GameState state) {
		if (state.moveLeft()) {
			movement -= MOVEMENT_SPEED;
		}

		if (state.moveRight()) {
			movement += MOVEMENT_SPEED;
		}
	}

	@Override
	public void shoot(GameState state) {
		if (!state.shoot()) {
			return;
		}

		if (firingCooldown != 0) {
			return;
		}

		int projectileX = getX() + getHitbox().width / 2;
		int projectileY = getY();

		Projectile projectile = new Projectile(this, projectileX, projectileY, ResourceCache.PLAYER); // TODO add actual player projectile resource to the resources folder
		state.spawn(projectile);

		firingCooldown = 20;
	}

	@Override
	public Team team() {
		 return team;
	}
}