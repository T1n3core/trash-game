package io.github.T1n3core.trash_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	private Thread gameThread;
	private ScreenState screenState;
	private boolean running;
	private GameState gameState;
	private boolean gameOver = false;
	private int difficulty;
	private final List<ScorePopup> scorePopups = new ArrayList<>();
	private EnemyController controller;
	private BackgroundMusicPlayer bgm;
	private static int bestScore = 0;
	private static final String HIGHSCORE_FILE = "highscore.dat";

	public GamePanel() {
		setFocusable(true);
		addKeyListener(this);
		SwingUtilities.invokeLater(this::requestFocusInWindow);

		screenState = ScreenState.MENU;

		loadHighScore();

		bgm = new BackgroundMusicPlayer("music.wav");
		bgm.play();
	}

	private void startGame(int startingDifficulty) {
		gameState = new GameState();
		controller = new EnemyController();

		difficulty = startingDifficulty;
		gameOver = false;
		running = true;

		gameState.spawn(new Player(GameConfig.SCREEN_WIDTH / 2, 750));
		spawnShields();

		gameThread = new Thread(this);
		gameThread.start();

		screenState = ScreenState.PLAYING;
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

		switch (screenState) {
			case MENU -> drawMenu(g);
			case LEVEL_SELECT -> drawLevelSelect(g);
			case CREDITS -> drawCredits(g);
			case PLAYING -> drawGame(g);
		}
	}

	private void drawMenu(Graphics g) {
		g.drawImage(ResourceCache.BACKGROUND_MENU, 0, 0, getWidth(), getHeight(), null);

		g.setColor(Color.WHITE);
		g.setFont(g.getFont().deriveFont(80f));
		g.drawString("TRASH GAME", getWidth() / 2 - 250, 200);

		g.setFont(g.getFont().deriveFont(40f));
		g.drawString("1 - Play", getWidth() / 2 - 100, 400);
		g.drawString("2 - Credits", getWidth() / 2 - 100, 460);
	}

	private void drawLevelSelect(Graphics g) {
		g.drawImage(ResourceCache.BACKGROUND_MENU, 0, 0, getWidth(), getHeight(), null);

		g.setColor(Color.WHITE);
		g.setFont(g.getFont().deriveFont(60f));
		g.drawString("Select Difficulty", getWidth() / 2 - 250, 200);

		g.setFont(g.getFont().deriveFont(40f));
		g.drawString("1 - Easy", getWidth() / 2 - 100, 350);
		g.drawString("2 - Medium", getWidth() / 2 - 100, 400);
		g.drawString("3 - Hard", getWidth() / 2 - 100, 450);
		g.drawString("4 - Insane", getWidth() / 2 - 100, 500);

		g.drawString("ESC - Back", getWidth() / 2 - 100, 600);
	}

	private void drawCredits(Graphics g) {
		g.drawImage(ResourceCache.BACKGROUND_MENU, 0, 0, getWidth(), getHeight(), null);

		g.setColor(Color.WHITE);
		g.setFont(g.getFont().deriveFont(50f));
		g.drawString("Credits", getWidth() / 2 - 100, 200);

		g.setFont(g.getFont().deriveFont(30f));
		g.drawString("Game by T1n3core", getWidth() / 2 - 150, 300);
		g.drawString("ESC - Back", getWidth() / 2 - 100, 500);
	}

	private void drawGame(Graphics g) {
		switch (difficulty) {
			case 1 -> g.drawImage(ResourceCache.BACKGROUND_DIFF_1, 0, 0, getWidth(), getHeight(), null);
			case 2 -> g.drawImage(ResourceCache.BACKGROUND_DIFF_2, 0, 0, getWidth(), getHeight(), null);
			case 3 -> g.drawImage(ResourceCache.BACKGROUND_DIFF_3, 0, 0, getWidth(), getHeight(), null);
			case 4 -> g.drawImage(ResourceCache.BACKGROUND_DIFF_4, 0, 0, getWidth(), getHeight(), null);
			default -> g.drawImage(ResourceCache.BACKGROUND_DIFF_5, 0, 0, getWidth(), getHeight(), null);
		}

		for (Entity e : gameState.getEntities()) {
			g.drawImage(e.getSprite(), e.getX(), e.getY(), null);

			if (GameConfig.SHOW_HITBOXES) {
				Rectangle hitbox = e.getHitbox();
				g.setColor(Color.red);
				g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
			}

		}

		Iterator<ScorePopup> iter = scorePopups.iterator();
		while (iter.hasNext()) {
			ScorePopup popup = iter.next();
			popup.draw(g);
			if (popup.update()) {
				iter.remove();
			}
		}

		if (gameOver) {
			g.setColor(Color.RED);

			g.setFont(g.getFont().deriveFont(java.awt.Font.BOLD, 80f));
			g.drawString("GAME OVER", getWidth() / 2 - 250, getHeight() / 2);

			g.setFont(g.getFont().deriveFont(java.awt.Font.PLAIN, 40f));
			g.drawString("Press R to restart", getWidth() / 2 - 180, getHeight() / 2 + 40);
		}

		g.setColor(Color.BLACK);
		g.setFont(g.getFont().deriveFont(20f));
		g.drawString("Score: " + gameState.getScore(), 20, 30);
		g.drawString("Best score: " + bestScore, 20, 60);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (GameConfig.PRINT_KEYBOARD_INPUT) {
			System.out.println("KEY: " + key);
		}

		switch (screenState) {
			case MENU -> {
				if (key == KeyEvent.VK_1) {
					screenState = ScreenState.LEVEL_SELECT;
				} else if (key == KeyEvent.VK_2) {
					screenState = ScreenState.CREDITS;
				}
			}

			case LEVEL_SELECT -> {
				if (key == KeyEvent.VK_1)
					startGame(1);
				if (key == KeyEvent.VK_2)
					startGame(2);
				if (key == KeyEvent.VK_3)
					startGame(3);
				if (key == KeyEvent.VK_4)
					startGame(4);

				if (key == KeyEvent.VK_ESCAPE) {
					screenState = ScreenState.MENU;
				}
			}

			case CREDITS -> {
				if (key == KeyEvent.VK_ESCAPE) {
					screenState = ScreenState.MENU;
				}
			}

			case PLAYING -> {
				if (gameOver) {
					if (e.getKeyCode() == KeyEvent.VK_R) {
						restartGame();
					}
					return;
				}

				switch (e.getKeyCode()) {
					case KeyEvent.VK_A, KeyEvent.VK_LEFT ->
						gameState.setMoveLeft(true);
					case KeyEvent.VK_D, KeyEvent.VK_RIGHT ->
						gameState.setMoveRight(true);
					case KeyEvent.VK_SPACE ->
						gameState.setShoot(true);
					case KeyEvent.VK_M -> {
						if (bgm.isPlaying())
							bgm.stop();
						else
							bgm.play();
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A, KeyEvent.VK_LEFT ->
				gameState.setMoveLeft(false);
			case KeyEvent.VK_D, KeyEvent.VK_RIGHT ->
				gameState.setMoveRight(false);
			case KeyEvent.VK_SPACE ->
				gameState.setShoot(false);
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
				case 1 ->
					EnemySpawner.formation1(gameState);
				case 2 ->
					EnemySpawner.formation2(gameState);
				case 3 ->
					EnemySpawner.formation3(gameState);
				case 4 ->
					EnemySpawner.formation4(gameState);
				default ->
					EnemySpawner.formation5(gameState);
			}

			difficulty++;
		}

		gameState.getPopupSpawnQueue().forEach(s -> scorePopups.add(s));
		gameState.commit();

		if (!playerAlive) {
			gameOver();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	private void loadHighScore() {
		try (DataInputStream in = new DataInputStream(new FileInputStream(HIGHSCORE_FILE))) {
			bestScore = in.readInt();
		} catch (IOException e) {
			bestScore = 0;
		}
	}

	private void saveHighScore() {
		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(HIGHSCORE_FILE))) {
			out.writeInt(bestScore);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void gameOver() {
		gameOver = true;
		running = false;

		if (gameState.getScore() > bestScore) {
			bestScore = gameState.getScore();
			saveHighScore();
		}
	}

	private void spawnShields() {
		int numShields = 4;
		int shieldSpacing = GameConfig.SCREEN_WIDTH / (numShields + 1);
		int shieldY = 500;

		for (int i = 1; i <= numShields; i++) {
			int shieldX = i * shieldSpacing - ResourceCache.SHIELD.getWidth();
			shieldX += 160;
			Shield shield = new Shield(shieldX, shieldY);
			gameState.spawn(shield);
		}
	}

	private void restartGame() {
		gameState = new GameState();
		controller = new EnemyController();

		difficulty = 1;
		gameOver = false;
		running = true;

		gameState.spawn(new Player(GameConfig.SCREEN_WIDTH / 2, 750));
		spawnShields();

		gameThread = new Thread(this);
		gameThread.start();
	}
}
