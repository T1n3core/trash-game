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
        
        while (running) {
            
            boolean playerAlive = false;

            for (Entity e : gameState.getEntities()) {
                if (e instanceof Player) {
                    playerAlive = true;
                }

                e.update(gameState);
            }

            if (!playerAlive) {
                gameOver();
            }

            repaint();

            try { // TODO might need to rewrite with fixed timing
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

        if (gameOver) {
            g.setFont(g.getFont().deriveFont(48f));
            g.drawString("GAME OVER", getWidth() / 2 - 150, getHeight() / 2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (gameOver) {
            return;
        }

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
        // TODO possibly rewrite this with switch case statement instead
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

    // TODO implement a game over method
    private void gameOver() {
        gameOver = true;
        running = false;
    }
}