package io.github.T1n3core.trash_game;

public class EnemyController {
    private int direction = 1;
    private static final int SPEED = 1;
    private static final int DROP_DISTANCE = 40;

    public void update(GameState state) {

        boolean shouldDrop = false;

        for (Entity e : state.getEntities()) {
            if (!(e instanceof Enemy enemy)) continue;

            int nextX = enemy.getX() + direction * SPEED;

            if (nextX <= 0 || nextX >= GameConfig.SCREEN_WIDTH - enemy.getHitbox().width) {
                shouldDrop = true;
                break;
            }
        }

        if (shouldDrop) {
            direction *= -1;

            for (Entity e : state.getEntities()) {
                if (e instanceof Enemy enemy) {
                    enemy.setY(enemy.getY() + DROP_DISTANCE);
                }
            }
        } else {
            for (Entity e : state.getEntities()) {
                if (e instanceof Enemy enemy) {
                    enemy.setX(enemy.getX() + direction * SPEED);
                }
            }
        }
    }
}
