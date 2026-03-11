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
        if (Math.random() > 0.02) {
            return;
        }

        int centerX = getX() + getHitbox().width / 2 - 5; // Assuming the projectiles are 10px
        int projectileY = getY() + getHitbox().height;

        int[] offsets = {-15, 0, 15};
        
        for (int offset : offsets) {
            try { // TODO constant projectile sprites to reduce syscall overhead
                Projectile projectile = new Projectile(this, centerX + offset, projectileY, ImageIO.read(new File("seed.png")));
                state.spawn(projectile);
            } catch (Exception e) {
                System.err.println("Could not load bio projectile sprite, projectile will not be created");
            }
        }
    }
}
