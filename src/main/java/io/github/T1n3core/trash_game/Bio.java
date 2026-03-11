package io.github.T1n3core.trash_game;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bio extends Enemy implements Shoots {
    public Bio(int x, int y) throws IOException {
        super(x, y, ImageIO.read(new File("bio.png")));
    }

    @Override
    public void shoot(GameState state) {
        // TODO Implement shoot(), this enemy type should shoot 3 at once like a shotgun
        
    }
}
