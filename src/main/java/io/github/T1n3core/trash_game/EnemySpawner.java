package io.github.T1n3core.trash_game;

import java.io.IOException;

public class EnemySpawner {

    private static final int startX = 100;
    private static final int startY = 50;

    private static final int columns = 8;
    private static final int spacingX = 70;
    private static final int spacingY = 60;

    public static void spawnWavePaper(GameState state) {
        try {
            for (int i = 0; i < columns; i++) {
                state.spawn(new Paper(startX + i * spacingX, startY + spacingY));
            }
        } catch (IOException e) {
            System.err.println("Failed to spawn Paper enemies");
            e.printStackTrace();
        }
    }

    public static void spawnWavePlastic(GameState state) {
        try {
            for (int i = 0; i < columns; i++) {
                state.spawn(new Plastic(startX + i * spacingX, startY + spacingY));
            }
        } catch (IOException e) {
            System.err.println("Failed to spawn Plastic enemies");
            e.printStackTrace();
        }
    }

    public static void spawnWaveBio(GameState state) {
        try {
            for (int i = 0; i < columns; i++) {
                state.spawn(new Bio(startX + i * spacingX, startY + spacingY));
            }
        } catch (IOException e) {
            System.err.println("Failed to spawn Bio enemies");
            e.printStackTrace();
        }
    }
}
