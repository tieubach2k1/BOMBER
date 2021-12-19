package uet.oop.bomberman;

import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.LevelPanel;
import uet.oop.bomberman.menu.MenuPanel;
import uet.oop.bomberman.play.PlayPanel;

import javax.swing.*;
import java.awt.*;

public class BombermanGame extends JFrame {
    public static final int INFO_HEIGHT = 50;
    public static final int SCREEN_WIDTH = 16;
    public static final int SCREEN_HEIGHT = 13;
    private MenuPanel menuPanel;
    private LevelPanel levelPanel;
    private PlayPanel playPanel;

    public static void main(String[] args) {
        new BombermanGame();
    }

    public BombermanGame() {
        setTitle("Bomberman Game");
        int reqWidth = SCREEN_WIDTH * Sprite.SCALED_SIZE;
        int reqHeight = SCREEN_HEIGHT * Sprite.SCALED_SIZE + INFO_HEIGHT;

        Insets insets = Toolkit.getDefaultToolkit()
                .getScreenInsets(getGraphicsConfiguration());

        setSize(reqWidth + 14, reqHeight + insets.bottom + 5);

        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        menuPanel = MenuPanel.getInstance(this::nextToPlayPanel);
        add(menuPanel);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void nextToPlayPanel() {
        setLayout(null);
        MenuPanel.getIntro().stop();
        menuPanel.setVisible(false);

        levelPanel = new LevelPanel(1);
        add(levelPanel);

        playPanel = new PlayPanel(1);
        playPanel.setNextLevel(this::nextLevel);
        playPanel.setBackMenu(this::backToMenu);
        add(playPanel);
        playPanel.requestFocus();

        validate();
        repaint();
    }

    private void nextLevel() {
        int nextLevel = levelPanel.getLevel() + 1;
        levelPanel = new LevelPanel(nextLevel);
        add(levelPanel);

        playPanel = new PlayPanel(nextLevel);
        playPanel.setNextLevel(this::nextLevel);
        playPanel.setBackMenu(this::backToMenu);
        add(playPanel);
        playPanel.requestFocus();

    }

    private void backToMenu() {
        setLayout(new BorderLayout());
        levelPanel.setVisible(false);
        playPanel.setVisible(false);
        remove(levelPanel);
        remove(playPanel);

        MenuPanel.getIntro().loop();
        menuPanel.setVisible(true);

        validate();
        repaint();
    }
}
