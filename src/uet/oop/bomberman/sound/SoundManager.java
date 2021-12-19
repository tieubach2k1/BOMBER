package uet.oop.bomberman.sound;

import javax.sound.sampled.*;
import java.io.IOException;

public class SoundManager {
    private final String path;
    private Clip clip;

    public SoundManager(String path) {
        this.path = path;
        loadFile();
    }

    private void loadFile() {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(
                    getClass()
                            .getResource(path)
                            .openStream()
            );
            clip = AudioSystem.getClip();
            clip.open(in);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(-1);
    }

    public void stop() {
        clip.stop();
    }
}
