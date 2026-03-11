package io.github.T1n3core.trash_game;

public class Main {
    public static void main(String[] args) {
        try {
            Game game = new Game();
            game.setVisible(true);
        } catch (Exception e) {
            System.err.println("Fatal exception, aborting");
            e.printStackTrace();
        }
    }
}
