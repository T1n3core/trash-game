package io.github.T1n3core.trash_game;

public final class GameConfig {
    public static final int SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT;
    public static final boolean SHOW_HITBOXES;
    public static final boolean PRINT_KEYBOARD_INPUT;

    static  {
        try {
            SCREEN_WIDTH = 1920;
            SCREEN_HEIGHT = 1080;
            SHOW_HITBOXES = false;
            PRINT_KEYBOARD_INPUT = false;
        } catch (Exception e) {
            throw new RuntimeException("Failed to set width and height");
        }
    }
}
