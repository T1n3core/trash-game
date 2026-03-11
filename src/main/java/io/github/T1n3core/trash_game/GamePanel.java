package io.github.T1n3core.trash_game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private final Thread gameThread;
    private final boolean running;
    private final GameState gameState;

    public GamePanel() throws IOException {
        setFocusable(true);
        addKeyListener(this);

        gameState = new GameState();

        running = true;

        gameState.spawn(new Player(600, 400, ImageIO.read(new File("player.png"))));

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (running) { 
            for (Entity e : gameState.getEntities()) {
                e.update(gameState);
            }

            repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();    
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Entity e : gameState.getEntities()) {
            g.drawImage(e.getSprite(), e.getX(), e.getY(), null);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            gameState.setMoveLeft(true);
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            gameState.setMoveRight(true);
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameState.setShoot(true);
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            gameState.setMoveLeft(true);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            gameState.setMoveRight(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            gameState.setMoveLeft(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            gameState.setMoveRight(false);
        }
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameState.setShoot(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            gameState.setMoveLeft(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            gameState.setMoveRight(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}