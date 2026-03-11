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

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> gameState.setMoveLeft(true);
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> gameState.setMoveRight(true);
            case KeyEvent.VK_SPACE -> gameState.setShoot(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                gameState.setMoveLeft(false);
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                gameState.setMoveRight(false);
                break;

            case KeyEvent.VK_SPACE:
                gameState.setShoot(false);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void gameOver() {
        gameOver = true;
        running = false;
    }
}