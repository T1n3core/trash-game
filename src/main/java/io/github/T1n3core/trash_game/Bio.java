package io.github.T1n3core.trash_game;

public class Bio extends Enemy implements Shoots {
    private int firingCooldown;

    public Bio(int x, int y) {
        super(x, y, ResourceCache.BIO);
        firingCooldown = 90;
    }

    @Override
    public void shoot(GameState state) {
        if (Math.random() > 0.02) {
            return;
        }

        if (firingCooldown != 0) {
            firingCooldown--;
            return;
        }

        int centerX = getX() + getHitbox().width / 2 - 5; // Assuming the projectiles are 10px
        int projectileY = getY() + getHitbox().height;

        int[] offsets = {-15, 0, 15};
        
        for (int offset : offsets) {
            Projectile projectile = new Projectile(this, centerX + offset, projectileY, ResourceCache.SEED);
            state.spawn(projectile);
        }

        firingCooldown = 180;
    }
}
