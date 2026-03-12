package io.github.T1n3core.trash_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Thread gameThread;
    private boolean running;
    private GameState gameState;
    private boolean gameOver = false;
    private int difficulty;
    private EnemyController controller;

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);
        SwingUtilities.invokeLater(this::requestFocusInWindow);

        difficulty = 1;

        gameState = new GameState();

        controller = new EnemyController();

        running = true;

        gameState.spawn(new Player(GameConfig.SCREEN_WIDTH / 2, 750));
        spawnShields();

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

            // Colored hitbox
            Rectangle hitbox = e.getHitbox();
            g.setColor(Color.red);
            g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
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
        System.out.println("KEY: " + e.getKeyCode());

        if (gameOver) {
            return;
        }

        System.out.println("KEY: " + e.getKeyCode());

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
        gameState.commit();

        boolean playerAlive = false;
        boolean enemiesDead = true;

        controller.update(gameState);

        for (Entity e : gameState.getEntities()) {
            if (e instanceof Player) {
                playerAlive = true;
            }

            if (e instanceof Enemy) {
                enemiesDead = false;
            }

            e.update(gameState);
        }

        if (enemiesDead) {
            switch (difficulty) {
                case 1 -> EnemySpawner.formation1(gameState);
                case 2 -> EnemySpawner.formation2(gameState);
                case 3 -> EnemySpawner.formation3(gameState);
                case 4 -> EnemySpawner.formation4(gameState);
                default -> EnemySpawner.formation5(gameState);
            }

            difficulty++;
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

    private void spawnShields() {
        int numShields = 4;
        int shieldSpacing = GameConfig.SCREEN_WIDTH / (numShields + 1);
        int shieldY = 500;

        for (int i=1; i <= numShields; i++) {
            int shieldX = i * shieldSpacing - ResourceCache.SHIELD.getWidth();
            shieldX += 160;
            Shield shield = new Shield(shieldX, shieldY);
            gameState.spawn(shield);
        }
    }
}