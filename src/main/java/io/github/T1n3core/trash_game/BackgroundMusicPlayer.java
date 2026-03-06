package io.github.T1n3core.trash_game;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BackgroundMusicPlayer implements Runnable {
    private final String filePath;
    private volatile boolean isPlaying = true;

    public BackgroundMusicPlayer(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filePath))) {
            AudioFormat format = audioIn.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                line.open(format);
                line.start();

                byte[] buffer = new byte[4096];
                int bytesRead;

                while (isPlaying) {
                    audioIn.mark(Integer.MAX_VALUE);
                    while ((bytesRead = audioIn.read(buffer, 0, buffer.length)) != -1 && isPlaying) {
                        line.write(buffer, 0, bytesRead);
                    }
                    audioIn.reset();
                }
                line.drain();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        isPlaying = false;
    }
}
