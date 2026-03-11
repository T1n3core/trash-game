package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;
import java.io.File;
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

    static {
        try {
            PLAYER = ImageIO.read(new File("player.png"));
            BIO = ImageIO.read(new File("bio.png"));
            PAPER = ImageIO.read(new File("paper.png"));
            PLASTIC = ImageIO.read(new File("plastic.png"));
            PLANE = ImageIO.read(new File("plane.png"));
            SEED = ImageIO.read(new File("seed.png"));
            BACKGROUND = ImageIO.read(new File("background.png"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resources", e);
        }
    }

    private ResourceCache() {}
}