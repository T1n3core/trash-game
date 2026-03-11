package io.github.T1n3core.trash_game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    private Thread gameThread;
    private boolean running = false;

    private Player player;
    private GameState gameState;

    public GamePanel() {

        setFocusable(true);
        addKeyListener(this);

        gameState = new GameState();
        player = new Player(400, 500);

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        while (running) { 
            
            update();
            repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();    
            }
        }
    }

    private void update() {
        player.update(gameState);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(player.getSprite(), player.getX(), player.getY(), null);
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
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}