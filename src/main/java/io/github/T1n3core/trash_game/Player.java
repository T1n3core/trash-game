package io.github.T1n3core.trash_game;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Player extends Entity implements Movable, Shoots {
    public Player(int x, int y) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("player.png"));
        } catch (Exception e) {
            System.err.println("Could not load player sprite");
            e.printStackTrace();
            throw new IllegalStateException("Player does not exist");
        }
        super(x, y, img.getWidth(), img.getHeight(), img);
    }

    @Override
    public void update(GameState state) {
        if (state.moveLeft()) {
            
        }
    }
}
