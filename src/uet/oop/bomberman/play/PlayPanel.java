package uet.oop.bomberman.play;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.INextPanel;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayPanel extends JPanel implements KeyListener {
    public static boolean isRunning = true;
    public static boolean isWin;
    private INextPanel nextLevel;
    private INextPanel backMenu;
    private final PlayManager playManager;

    private final SoundManager winSound = new SoundManager("/audios/level-complete.wav");
    private final SoundManager failSound = new SoundManager("/audios/game-over.wav");

    public PlayPanel(int level) {
        isRunning = true;
        isWin = false;
        playManager = new PlayManager(level);
        setSize(PlayManager.MAP_WIDTH * Sprite.SCALED_SIZE,
                PlayManager.MAP_HEIGHT * Sprite.SCALED_SIZE);
        setLocation(0, BombermanGame.INFO_HEIGHT);
        setLayout(null);
        setFocusable(true);
        addKeyListener(this);
        initThread();
    }

    public void setBackMenu(INextPanel backMenu) {
        this.backMenu = backMenu;
    }

    public void setNextLevel(INextPanel nextLevel) {
        this.nextLevel = nextLevel;
    }

    private void initThread() {
        new Thread(() -> {
            while (isRunning) {
                playManager.update();
                repaint();
            }
            playManager.stopAllSound();
            if (isWin) {
                winSound.play();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                winSound.stop();
                nextLevel.next();
            } else {
                failSound.play();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                failSound.stop();
                backMenu.next();
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        playManager.renderAll(g2d);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        playManager.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        playManager.keyReleased(e);
    }

}
