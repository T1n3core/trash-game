package io.github.T1n3core.trash_game;

public class Bio extends Enemy implements Shoots {
    private int firingCooldown;

    public Bio(int x, int y) {
        super(x, y, ResourceCache.BIO);
        firingCooldown = 90;
    }

    @Override
    public void shoot(GameState state) {
        if (firingCooldown != 0) {
            firingCooldown--;
            return;
        }

        if (Math.random() > 0.02) {
            firingCooldown = 15;
            return;
        }

        int centerX = getX() + getHitbox().width / 2 - 5;
        int projectileY = getY() + getHitbox().height;

        int[] offsets = {-45, 0, 45};
        
        for (int offset : offsets) {
            Projectile projectile = new Projectile(this, centerX + offset, projectileY, ResourceCache.SEED);
            state.spawn(projectile);
        }

        firingCooldown = 180;
    }
}
