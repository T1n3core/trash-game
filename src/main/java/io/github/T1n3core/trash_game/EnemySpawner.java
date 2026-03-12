package io.github.T1n3core.trash_game;

public class EnemySpawner {

    private static final int startX = 100;
    private static final int startY = 50;
    private static final int columns = 8;
    private static final int spacingX = 70;
    private static final int spacingY = 60;

    public static void spawnWavePaper(GameState state, int offsetY) {
        for (int i = 0; i < columns; i++) {
            state.spawn(new Paper(startX + i * spacingX, startY + offsetY));
        }
    }

    public static void spawnWavePlastic(GameState state, int offsetY) {
        for (int i = 0; i < columns; i++) {
            state.spawn(new Plastic(startX + i * spacingX, startY + offsetY));
        }
    }

    public static void spawnWaveBio(GameState state, int offsetY) {
        for (int i = 0; i < columns; i++) {
            state.spawn(new Bio(startX + i * spacingX, startY + offsetY));
        }
    }

    public static void formation1(GameState state) {
        spawnWavePaper(state, 0);
        spawnWavePlastic(state, spacingY);
        spawnWavePlastic(state, spacingY * 2);
        spawnWavePlastic(state, spacingY * 3);
        spawnWavePlastic(state, spacingY * 4);
    }

    public static void formation2(GameState state) {
        spawnWavePaper(state, 0);
        spawnWavePaper(state, spacingY);
        spawnWavePlastic(state, spacingY * 2);
        spawnWavePlastic(state, spacingY * 3);
        spawnWavePlastic(state, spacingY * 4);
    }

    public static void formation3(GameState state) {
        spawnWaveBio(state, 0);
        spawnWavePaper(state, spacingY);
        spawnWavePaper(state, spacingY * 2);
        spawnWavePlastic(state, spacingY * 3);
        spawnWavePlastic(state, spacingY * 4);
    }

    public static void formation4(GameState state) {
        spawnWaveBio(state, 0);
        spawnWavePaper(state, spacingY);
        spawnWavePaper(state, spacingY * 2);
        spawnWavePaper(state, spacingY * 3);
        spawnWavePlastic(state, spacingY * 4);
    }

    public static void formation5(GameState state) {
        spawnWaveBio(state, 0);
        spawnWaveBio(state, spacingY);
        spawnWavePaper(state, spacingY * 2);
        spawnWavePaper(state, spacingY * 3);
        spawnWavePlastic(state, spacingY * 4);
    }
}
