package uet.oop.bomberman.character;

import uet.oop.bomberman.animation.Animation;
import uet.oop.bomberman.bomb.Bomb;
import uet.oop.bomberman.character.enemy.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mapEntity.Grass;
import uet.oop.bomberman.entities.mapEntity.LayerEntity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.item.*;
import uet.oop.bomberman.play.PlayManager;
import uet.oop.bomberman.play.PlayPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bomber extends Character {
    private int maxBombLength = 1;
    private boolean startDead;
    private int bombLeft = 1;
    private int posX = -1;
    private int posY = -1;
    private int imgX;
    private int imgY;

    private int squareX;
    private int squareY;
    private final int[] traceBack = new int[PlayManager.MAP_WIDTH * PlayManager.MAP_HEIGHT];

    public int getMaxBombLength() {
        return maxBombLength;
    }

    public boolean isStartDead() {
        return startDead;
    }

    public int getBombLeft() {
        return bombLeft;
    }

    public void setBombLeft(int bombLeft) {
        this.bombLeft = bombLeft;
    }

    public Bomber(int x, int y) {
        super(x, y);
        squareX = x;
        squareY = y;
        img = Sprite.player_right.getImage();
        delayMove = 10;
        size = Sprite.SCALED_SIZE;
    }

    public int[] getTraceBack() {
        return traceBack;
    }

    public boolean isMoveSquare() {
        int vX = x / Sprite.SCALED_SIZE;
        int vY = y / Sprite.SCALED_SIZE;
        if (vX != squareX || vY != squareY) {
            squareX = vX;
            squareY = vY;
            return true;
        }
        return false;
    }

    private final int[] dx = {0, 0, 1, -1};
    private final int[] dy = {1, -1, 0, 0};

    public void calculateTraceBack(List<Entity> entities) {
        for (int i = 0; i < PlayManager.MAP_WIDTH * PlayManager.MAP_HEIGHT; i++) traceBack[i] = -1;
        List<Integer> qX = new ArrayList<>();
        List<Integer> qY = new ArrayList<>();
        boolean[][] visited = new boolean[102][102];
        qX.add(getSquareX(x));
        qY.add(getSquareY(y));
        while (!qX.isEmpty() && !qY.isEmpty()) {
            int xx = qX.get(0);
            int yy = qY.get(0);
            qX.remove(0);
            qY.remove(0);
            for (int i = 0; i < 4; i++) {
                int u = xx + dx[i];
                int v = yy + dy[i];
                if (u < 0 || v < 0 || u >= PlayManager.MAP_WIDTH || v >= PlayManager.MAP_HEIGHT || visited[u][v])
                    continue;
                visited[u][v] = true;
                traceBack[u + PlayManager.MAP_WIDTH * v] = i;
                Entity e = entities.get(u + PlayManager.MAP_WIDTH * v);
                Entity topEntity = e.getTopEntity();
                if ((topEntity instanceof Grass) || (topEntity instanceof Item)) {
                    qX.add(u);
                    qY.add(v);
                }
            }
        }
    }

    @Override
    public void initMoveAnimation() {
        super.initMoveAnimation();

        moveAnimation[UP].addImage(Sprite.player_up.getImage());
        moveAnimation[UP].addImage(Sprite.player_up_1.getImage());
        moveAnimation[UP].addImage(Sprite.player_up_2.getImage());
        moveAnimation[UP].setTimeDelay(100);

        moveAnimation[DOWN].addImage(Sprite.player_down.getImage());
        moveAnimation[DOWN].addImage(Sprite.player_down_1.getImage());
        moveAnimation[DOWN].addImage(Sprite.player_down_2.getImage());
        moveAnimation[DOWN].setTimeDelay(100);

        moveAnimation[LEFT].addImage(Sprite.player_left.getImage());
        moveAnimation[LEFT].addImage(Sprite.player_left_1.getImage());
        moveAnimation[LEFT].addImage(Sprite.player_left_2.getImage());
        moveAnimation[LEFT].setTimeDelay(100);

        moveAnimation[RIGHT].addImage(Sprite.player_right.getImage());
        moveAnimation[RIGHT].addImage(Sprite.player_right_1.getImage());
        moveAnimation[RIGHT].addImage(Sprite.player_right_2.getImage());
        moveAnimation[RIGHT].setTimeDelay(100);
    }

    @Override
    public void createDeadAnimation() {
        startDead = true;
        deadAnimation = new Animation(Animation.ONE_CYCLE);
        deadAnimation.addImage(Sprite.player_dead1.getImage());
        deadAnimation.addImage(Sprite.player_dead2.getImage());
        deadAnimation.addImage(Sprite.player_dead3.getImage());
        deadAnimation.setTimeDelay(400);
    }

    @Override
    protected void move() {
        switch (direction) {
            case UP -> {
                if (posX != -1) x = imgX;
                y--;
            }
            case DOWN -> {
                if (posX != -1) x = imgX;
                y++;
            }
            case LEFT -> {
                if (posY != -1) y = imgY;
                x--;
            }
            case RIGHT -> {
                if (posY != -1) y = imgY;
                x++;
            }
        }
    }

    @Override
    public boolean isCollide(Entity e) {
        int dx = 0, dy = 0, sz = Sprite.SCALED_SIZE;
        switch (direction) {
            case UP -> dy = -1;
            case DOWN -> dy = 1;
            case LEFT -> dx = -1;
            case RIGHT -> dx = 1;
        }

        posX = ((x % sz) >= Math.floor(0.6 * sz) ? 1 : ((x % sz) <= Math.round(0.3 * sz) ? 0 : -1));
        posY = ((y % sz) >= Math.floor(0.6 * sz) ? 1 : ((y % sz) <= Math.round(0.3 * sz) ? 0 : -1));

        if (direction == UP || direction == DOWN) {
            imgX = (posX != -1 ? (posX + x / sz) * sz : x);
            imgY = y;
        } else {
            imgX = x;
            imgY = (posY != -1 ? (posY + y / sz) * sz : y);
        }
        Rectangle rThis = new Rectangle(imgX + dx, imgY + dy,
                size - 5, size);
        Rectangle rEntity = new Rectangle(e.getX(), e.getY(),
                size, size);
        return rThis.intersects(rEntity);
    }
    
    @Override
    public void detectCollision(List<Entity> entities) {
        for (Entity e : entities) {
            Entity topEntity = e.getTopEntity();
            if (Math.abs(topEntity.getX() - x) > size * 2 || Math.abs(topEntity.getY() - y) > size * 2) continue;
            if (!(topEntity instanceof Grass) && !(topEntity instanceof Item) && !(topEntity instanceof Bomb)
                    && isCollide(topEntity)) {
                stop = true;
            }
            if (topEntity instanceof Bomb) {
                Bomb b = (Bomb) topEntity;
                if (isCollide(b) && !b.isAllowToPass()) {
                    stop = true;
                    return;
                } else if (!isCollide(b)) {
                    b.setAllowToPass(false);
                }
            }
        }
    }

    public void detectBonusItem(List<Entity> entities) {
        for (Entity e : entities) {
            if (e instanceof LayerEntity) {
                LayerEntity layer = (LayerEntity) e;
                Entity topEntity = layer.getTopEntity();
                if (topEntity instanceof Item && topEntity.isCollide(this)) {
                    Item item = (Item) topEntity;
                    item.setRemove(true);
                    if (item instanceof BombItem) bombLeft++;
                    if (item instanceof SpeedItem) delayMove--;
                    if (item instanceof FlameItem) maxBombLength++;
                    if (item instanceof Portal) {
                        PlayPanel.isRunning = false;
                        PlayPanel.isWin = true;
                    }
                    return;
                }
            }
        }
    }

    public void detectEnemy(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (isCollide(enemy)) {
                stop = true;
                createDeadAnimation();
                return;
            }
        }
    }
}
