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
	public static final BufferedImage BACKGROUND;
	public static final BufferedImage ICON;
	public static final BufferedImage SHIELD;
	public static final BufferedImage LASER;

	static {
		try {
			PLAYER = load("player.png");
			BIO = load("bio.png");
			PAPER = load("paper.png");
			PLASTIC = load("plastic.png");
			PLANE = load("plane.png");
			SEED = load("seed.png");
			BACKGROUND = load("background.png");
			ICON = load("game_icon.png");
			SHIELD = load("shield.png");
			LASER = load("laser.png");
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
