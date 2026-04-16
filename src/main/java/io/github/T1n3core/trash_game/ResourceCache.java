package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class ResourceCache {
	public static final BufferedImage PLAYER;
	public static final BufferedImage BIO;
	public static final BufferedImage PAPER;
	public static final BufferedImage PLASTIC;
	public static final BufferedImage PLANE;
	public static final BufferedImage SEED;
	public static final BufferedImage BACKGROUND_MENU;
	public static final BufferedImage BACKGROUND_DIFF_1;
	public static final BufferedImage BACKGROUND_DIFF_2;
	public static final BufferedImage BACKGROUND_DIFF_3;
	public static final BufferedImage BACKGROUND_DIFF_4;
	public static final BufferedImage BACKGROUND_DIFF_5;
	public static final BufferedImage ICON;
	public static final BufferedImage SHIELD;
	public static final BufferedImage LASER;
	public static final BufferedImage HEART;

	static {
		try {
			PLAYER = load("player.png");
			BIO = load("bio.png");
			PAPER = load("paper.png");
			PLASTIC = load("plastic.png");
			PLANE = load("plane.png");
			SEED = load("seed.png");
			BACKGROUND_MENU = load("background.png");
			BACKGROUND_DIFF_1 = load("background1.png");
			BACKGROUND_DIFF_2 = load("background2.png");
			BACKGROUND_DIFF_3 = load("background3.png");
			BACKGROUND_DIFF_4 = load("background4.png");
			BACKGROUND_DIFF_5 = load("background5.png");
			ICON = load("game_icon.png");
			SHIELD = load("shield.png");
			LASER = load("laser.png");
			HEART = load("heart.png");
		} catch (IOException e) {
			throw new RuntimeException("Failed to load resources", e);
		}
	}

	private static BufferedImage load(String name) throws IOException {
		return ImageIO.read(ResourceCache.class.getResourceAsStream("/" + name));
	}

	private ResourceCache() {
		throw new UnsupportedOperationException("Utility class, no instantiation");
	}
}
