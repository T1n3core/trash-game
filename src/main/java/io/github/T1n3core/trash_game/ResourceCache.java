package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class ResourceCache {
    public final BufferedImage player;
    public final BufferedImage bio;
    public final BufferedImage paper;
    public final BufferedImage plastic;
    public final BufferedImage plane;
    public final BufferedImage seed;

    public ResourceCache() throws IOException {
        player = ImageIO.read(new File("player.png"));
        bio = ImageIO.read(new File("bio.png"));
        paper = ImageIO.read(new File("paper.png"));
        plastic = ImageIO.read(new File("plastic.png"));
        plane = ImageIO.read(new File("plane.png"));
        seed = ImageIO.read(new File("seed.png"));
    }
}
