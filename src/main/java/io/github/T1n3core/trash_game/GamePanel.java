package io.github.T1n3core.trash_game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Thread gameThread;
    private boolean running;
    private GameState gameState;
    private boolean gameOver = false;

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        gameState = new GameState();

        running = true;

        gameState.spawn(new Player(600, 400));

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        final int TARGET_FPS = 60;
        final double TIME_PER_FRAME = 1_000_000_000.0 / TARGET_FPS;

        long lastTime = System.nanoTime();
        double delta = 0;
        
        while (running) {

            long now = System.nanoTime();
            delta += (now - lastTime) / TIME_PER_FRAME;
            lastTime = now;

            while (delta >= 1) {
                updateGame();
                delta--;
            }
            
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(ResourceCache.BACKGROUND, 0, 0, getWidth(), getHeight(), null);

        for (Entity e : gameState.getEntities()) {
            g.drawImage(e.getSprite(), e.getX(), e.getY(), null);
        }

        if (gameOver) {
            g.setFont(g.getFont().deriveFont(48f));
            g.drawString("GAME OVER", getWidth() / 2 - 150, getHeight() / 2);
        }

        g.setFont(g.getFont().deriveFont(20f));
        g.drawString("Score: " + gameState.getScore(), 20, 30);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (gameOver) {
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> gameState.setMoveLeft(true);
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> gameState.setMoveRight(true);
            case KeyEvent.VK_SPACE -> gameState.setShoot(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> gameState.setMoveLeft(false);
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> gameState.setMoveRight(false);
            case KeyEvent.VK_SPACE -> gameState.setShoot(false);
        }
    }

    private void updateGame() {

        boolean playerAlive = false;

        for (Entity e : gameState.getEntities()) {
            if (e instanceof Player) {
                playerAlive = true;
            }

            e.update(gameState);
        }

        gameState.commit();

        if (!playerAlive) {
            gameOver();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void gameOver() {
        gameOver = true;
        running = false;
    }
}