package io.github.T1n3core.trash_game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ScorePopup {
	private int x, y;
	private int lifetime;
	private float alpha;
	private final int value;

	public ScorePopup(int x, int y, int value) {
		this.x = x;
		this.y = y;
		lifetime = 60;
		alpha = 1.0f;
		this.value = value;
	}

	public boolean update() {
		y--;
		lifetime--;
		alpha = Math.max(0, lifetime / 60.0f);
		return lifetime <= 0;
	}

	public void draw(Graphics g) {
		g.setColor(new Color(255, 255, 0, (int) (alpha * 255.0f)));
		g.setFont(g.getFont().deriveFont(Font.BOLD, 20.0f));
		g.drawString("+" + value, x, y);
	}
}
