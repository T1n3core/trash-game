package io.github.T1n3core.trash_game;

import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class BackgroundMusicPlayer {
	private final String filePath;
	private Thread playerThread;
	private volatile boolean playing = false;
	private volatile boolean stopRequested = false;

	public BackgroundMusicPlayer(String filePath) {
		this.filePath = filePath;
	}

	public synchronized void play() {
		if (playing)
			return;
		stopRequested = false;
		playerThread = new Thread(this::playLoop, "BackgroundMusicThread");
		playerThread.start();
		playing = true;
	}

	public synchronized void stop() {
		stopRequested = true;
		playing = false;
		if (playerThread != null) {
			playerThread.interrupt();
			playerThread = null;
		}
	}

	private void playLoop() {
		while (!stopRequested) {
			try (AudioInputStream audioIn = AudioSystem
					.getAudioInputStream(getClass().getClassLoader().getResource(filePath))) {
				AudioFormat format = audioIn.getFormat();
				DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

				try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
					line.open(format);
					line.start();

					byte[] buffer = new byte[4096];
					int bytesRead;
					while ((bytesRead = audioIn.read(buffer, 0, buffer.length)) != -1 && !stopRequested) {
						line.write(buffer, 0, bytesRead);
					}
					line.drain();
				}
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public synchronized boolean isPlaying() {
		return playing;
	}
}
