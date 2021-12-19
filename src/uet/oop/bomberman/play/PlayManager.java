package uet.oop.bomberman.play;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.bomb.Bomb;
import uet.oop.bomberman.character.Bomber;
import uet.oop.bomberman.character.Character;
import uet.oop.bomberman.character.enemy.Balloon;
import uet.oop.bomberman.character.enemy.Enemy;
import uet.oop.bomberman.character.enemy.smartEnemy.Doll;
import uet.oop.bomberman.character.enemy.smartEnemy.Kondoria;
import uet.oop.bomberman.character.enemy.smartEnemy.Oneal;
import uet.oop.bomberman.character.enemy.smartEnemy.SmartEnemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mapEntity.Brick;
import uet.oop.bomberman.entities.mapEntity.Grass;
import uet.oop.bomberman.entities.mapEntity.LayerEntity;
import uet.oop.bomberman.entities.mapEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.item.BombItem;
import uet.oop.bomberman.item.FlameItem;
import uet.oop.bomberman.item.Portal;
import uet.oop.bomberman.item.SpeedItem;
import uet.oop.bomberman.sound.SoundManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PlayManager {
    private boolean isLeft, isRight, isUp, isDown;
    private Bomber bomber;
    private final List<Entity> entities = new ArrayList<>();
    private final List<Bomb> bombs = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();

    public static int MAP_WIDTH;
    public static int MAP_HEIGHT;
    private int leftLimit;
    private int rightLimit;
    private int upLimit;
    private int downLimit;

    private final SoundManager backgroundSound = new SoundManager("/audios/stage-main-theme.wav");
    private final SoundManager backgroundWithDoorSound = new SoundManager("/audios/stage-main-theme-find-the-door.wav");
    private final SoundManager endingSound = new SoundManager("/audios/ending-theme.wav");
    private boolean foundDoor;

    public PlayManager(int level) {
        if (level > 2) level = 1;
        String path = "/levels/Level" + level + ".txt";
        createMap(path);
        backgroundSound.loop();
    }

    private void calculateLimit(int row, int col) {
        MAP_WIDTH = col;
        MAP_HEIGHT = row;
        leftLimit = BombermanGame.SCREEN_WIDTH / 2 * Sprite.SCALED_SIZE;
        rightLimit = (MAP_WIDTH - (BombermanGame.SCREEN_WIDTH + 1) / 2) * Sprite.SCALED_SIZE;
        upLimit = BombermanGame.SCREEN_HEIGHT / 2 * Sprite.SCALED_SIZE;
        downLimit = (MAP_HEIGHT - (BombermanGame.SCREEN_HEIGHT + 1) / 2) * Sprite.SCALED_SIZE;
    }

    private void createMap(String filePath) {
        try {
            InputStream in = getClass().getResource(filePath).openStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));

            String[] num = read.readLine().split("\\s");
            int r = Integer.parseInt(num[1]);
            int c = Integer.parseInt(num[2]);

            calculateLimit(r, c);

            for (int i = 0; i < r; i++) {
                String row = read.readLine();
                for (int j = 0; j < c; j++) {
                    Entity object;
                    char ch = row.charAt(j);
                    switch (ch) {
                        case '#' -> object = new Wall(j, i);
                        case '*' -> object = new LayerEntity(j, i,
                                new Grass(j, i), new Brick(j, i));
                        case 'x' -> object = new LayerEntity(j, i,
                                new Grass(j, i), new Portal(j, i), new Brick(j, i));
                        case 'p' -> {
                            bomber = new Bomber(j, i);
                            object = new LayerEntity(j, i, new Grass(j, i));
                        }
                        case '1' -> {
                            object = new LayerEntity(j, i, new Grass(j, i));
                            Balloon balloon = new Balloon(j, i);
                            enemies.add(balloon);
                        }
                        case '2' -> {
                            object = new LayerEntity(j, i, new Grass(j, i));
                            Oneal oneal = new Oneal(j, i);
                            enemies.add(oneal);
                        }
                        case '3' -> {
                            object = new LayerEntity(j, i, new Grass(j, i));
                            Doll doll = new Doll(j, i);
                            enemies.add(doll);
                        }
                        case 'b' -> object = new LayerEntity(j, i,
                                new Grass(j, i), new BombItem(j, i), new Brick(j, i));
                        case 'f' -> object = new LayerEntity(j, i,
                                new Grass(j, i), new FlameItem(j, i), new Brick(j, i));
                        case 's' -> object = new LayerEntity(j, i,
                                new Grass(j, i), new SpeedItem(j, i), new Brick(j, i));
                        default -> object = new LayerEntity(j, i, new Grass(j, i));
                    }
                    entities.add(object);
                }
            }
            read.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bomber.calculateTraceBack(entities);
        for (Enemy e : enemies) {
            if (e instanceof SmartEnemy) {
                ((SmartEnemy) e).setBomber(bomber);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> isUp = true;
            case KeyEvent.VK_DOWN -> isDown = true;
            case KeyEvent.VK_LEFT -> isLeft = true;
            case KeyEvent.VK_RIGHT -> isRight = true;
            case KeyEvent.VK_SPACE -> placeBomb();
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> isUp = false;
            case KeyEvent.VK_DOWN -> isDown = false;
            case KeyEvent.VK_LEFT -> isLeft = false;
            case KeyEvent.VK_RIGHT -> isRight = false;
        }
    }

    private void placeBomb() {
        int xBomb = Math.round((float) bomber.getX() / Sprite.SCALED_SIZE);
        int yBomb = Math.round((float) bomber.getY() / Sprite.SCALED_SIZE);
        if (bomber.getBombLeft() > 0) {
            bomber.setBombLeft(bomber.getBombLeft() - 1);
            Bomb b = new Bomb(xBomb, yBomb, bomber.getMaxBombLength());
            b.setMapEntityList(entities);
            LayerEntity e = (LayerEntity) entities.get(xBomb + MAP_WIDTH * yBomb);
            e.addTop(b);
            bombs.add(b);
        }
        bomber.calculateTraceBack(entities);
    }

    public void renderAll(Graphics2D g2d) {
        g2d.translate(
                Math.min(0, Math.max(leftLimit - rightLimit, leftLimit - bomber.getX())),
                Math.min(0, Math.max(upLimit - downLimit, upLimit - bomber.getY()))
        );
        for (Entity e : entities) {
            e.render(g2d);
        }
        for (int i = 0; i < bombs.size(); i++) {
            Bomb b = bombs.get(i);
            if (b != null) b.render(g2d);
        }
        for (Enemy enemy : enemies) {
            enemy.render(g2d);
        }
        bomber.render(g2d);
    }

    public void update() {
        updateBomber();
        updateBombs();
        updateEnemies();
        updateMap();
    }


    private void updateBomber() {
        if (isLeft || isRight || isUp || isDown || bomber.isStartDead()) {
            if (isLeft) bomber.setDirection(Character.LEFT);
            if (isRight) bomber.setDirection(Character.RIGHT);
            if (isUp) bomber.setDirection(Character.UP);
            if (isDown) bomber.setDirection(Character.DOWN);
            bomber.detectCollision(entities);
            bomber.detectBonusItem(entities);
            bomber.update();
            if (bomber.isMoveSquare()) bomber.calculateTraceBack(entities);
        }
        if (!bomber.isStartDead()) {
            bomber.detectEnemy(enemies);
            bomber.detectFlame(bombs);
            if (!bomber.isStartDead()) bomber.setStop(false);
        }
        if (bomber.isDead()) {
            PlayPanel.isRunning = false;
        }
    }

    private void updateEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.detectCollision(entities);
            enemy.detectFlame(bombs);
            enemy.update();
            if (enemy.isDead()) {
                if (enemy instanceof Doll) {
                    Kondoria kondoria = new Kondoria(enemy.getX() / Sprite.SCALED_SIZE, enemy.getY() / Sprite.SCALED_SIZE);
                    kondoria.setBomber(bomber);
                    kondoria.setDirection(enemy.getDirection());
                    enemies.set(i, kondoria);
                } else enemies.remove(i--);
            }
        }
        Enemy.numEnemies = enemies.size();
        if (Enemy.numEnemies == 0) {
            backgroundWithDoorSound.stop();
            backgroundSound.stop();
            endingSound.play();
        }
    }

    private void updateBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            Bomb b = bombs.get(i);
            if (b == null) continue;
            b.update();
            if (b.isRemove()) {
                bombs.remove(i--);
                bomber.setBombLeft(bomber.getBombLeft() + 1);
            }
        }
    }

    private void updateMap() {
        for (Entity e : entities) {
            e.update();
            Entity topEntity = e.getTopEntity();
            if (!foundDoor) {
                if (topEntity instanceof Portal) {
                    foundDoor = true;
                    backgroundSound.stop();
                    backgroundWithDoorSound.loop();
                }
            }
            if (topEntity.isRemove()) {
                ((LayerEntity) e).removeTop();
                bomber.calculateTraceBack(entities);
            }
        }
    }

    public void stopAllSound() {
        backgroundSound.stop();
        backgroundWithDoorSound.stop();
        endingSound.stop();
    }
}
