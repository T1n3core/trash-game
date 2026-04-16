package io.github.T1n3core.trash_game;

public class Player extends Entity implements Movable, Shoots {
	private int movement;
	private int firingCooldown;
	private int lives;
	private int invulFrames;
	private static final int MAX_LIVES = 3;
	private static final int INVUL_WINDOW = 100;
	private static final int MOVEMENT_SPEED = 10;
	public static final Team team = Team.FRIENDLY;

	public Player(int x, int y) {
		super(x, y, ResourceCache.PLAYER.getWidth(), ResourceCache.PLAYER.getHeight(), ResourceCache.PLAYER);
		movement = 0;
		firingCooldown = 0;
		lives = MAX_LIVES;
		invulFrames = INVUL_WINDOW;
		shrinkHitbox(160, 80);
		offsetHitbox(80, 0);
	}

	@Override
	public void update(GameState state) {
		handleInput(state);
		move(state);
		shoot(state);
		if (invulFrames > 0)
			invulFrames--;
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

		if (firingCooldown > 0) {
			return;
		}

		int projectileX = getX() + getHitbox().width / 2 + 60;
		int projectileY = getY() - 115;

		Projectile projectile = new Projectile(this, projectileX, projectileY, ResourceCache.LASER);
		projectile.shrinkHitbox(70, 20);
		state.spawn(projectile);

		firingCooldown = 40;
	}

	@Override
	public Team team() {
		return team;
	}

	public void damage(GameState state) {
		if (invulFrames > 0)
			return;

		lives--;
		invulFrames = INVUL_WINDOW;

		if (lives <= 0)
			state.kill(this);
	}

	public int lives() {
		return lives;
	}
}
