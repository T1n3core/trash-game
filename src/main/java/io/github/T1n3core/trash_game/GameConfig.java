package io.github.T1n3core.trash_game;

public final class GameConfig {
    public static final int SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT;

    static  {
        try {
            SCREEN_WIDTH = 1920;
            SCREEN_HEIGHT = 1080;
        } catch (Exception e) {
            throw new RuntimeException("Failed to set width and height");
        }
    }
}
