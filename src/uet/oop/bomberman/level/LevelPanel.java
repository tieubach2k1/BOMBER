package uet.oop.bomberman.level;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.play.PlayPanel;

import javax.swing.*;
import java.awt.*;

public class LevelPanel extends JPanel {
    private final int level;
    private Timer timer;
    private JTextField timeText;
    private int time = 180;
    private JTextField levelText;

    public LevelPanel(int level) {
        setSize(BombermanGame.SCREEN_WIDTH * Sprite.SCALED_SIZE, BombermanGame.INFO_HEIGHT);
        setLayout(null);
        this.level = level;
        initTextField();
        initTimer();
    }

    public int getLevel() {
        return level;
    }

    private void initTimer() {
        timer = new Timer(1000, e -> {
            time--;
            timeText.setText("Time: " + time);
            repaint();
            if (time == 0) {
                timer.stop();
                PlayPanel.isRunning = false;
            }
            if (!PlayPanel.isRunning) {
                timer.stop();
            }
        });
        timer.start();
    }

    private void initTextField() {
        Font f = new Font("Tahoda", Font.BOLD, 20);

        timeText = new JTextField("Time: " + time);
        timeText.setSize(getWidth() / 2, getHeight());
        timeText.setEditable(false);
        timeText.setBackground(Color.GREEN);
        timeText.setFont(f);
        timeText.setHorizontalAlignment(JTextField.CENTER);
        add(timeText);

        levelText = new JTextField("Level " + level);
        levelText.setSize(getWidth() / 2, getHeight());
        levelText.setLocation(getWidth() / 2, 0);
        levelText.setEditable(false);
        levelText.setBackground(Color.GREEN);
        levelText.setFont(f);
        levelText.setHorizontalAlignment(JTextField.CENTER);
        add(levelText);
        invalidate();
    }
}
