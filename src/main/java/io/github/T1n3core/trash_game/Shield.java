package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;

public class Shield extends Entity {
	private boolean damage;
	private int health;
	public static final Team team = Team.NEUTRAL;

	public Shield(int x, int y, BufferedImage sprite) {
		super(x, y, sprite.getWidth(), sprite.getHeight(), sprite);
		damage = false;
		health = 10;
	}

	@Override
	public void update(GameState state) {
		if (damage) {
			health--;
			damage = false;
		}

		if (health < 1) {
			state.kill(this);
		}
	}

	public void damage() {
		damage = true;
	}

	@Override
	public Team team() {
		return team;
	}
}
