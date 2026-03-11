package io.github.T1n3core.trash_game;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Plastic extends Enemy {
    public Plastic(int x, int y) throws IOException {
        super(x, y, ImageIO.read(new File("plastic.png")));
    }
}
