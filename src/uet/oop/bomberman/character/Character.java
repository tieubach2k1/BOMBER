package uet.oop.bomberman.character;

import uet.oop.bomberman.animation.Animation;
import uet.oop.bomberman.bomb.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mapEntity.Grass;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.item.Item;

import java.awt.*;
import java.util.List;

public abstract class Character extends Entity {
    protected Animation[] moveAnimation = new Animation[4];
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    protected Animation deadAnimation;
    protected int direction = RIGHT;
    private boolean isDead;
    protected boolean stop;

    protected int delayMove;

    private long lastTimeMove = 0;

    public Character(int x, int y) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        size = Sprite.SCALED_SIZE;
        initMoveAnimation();
        img = moveAnimation[direction].getImage();
    }

    //TODO: set x, y theo ô

    protected int getSquareX(int x) {
        return x / size;
    }

    protected int getSquareY(int y) {
        return y / size;
    }

    public void setDelayMove(int delayMove) {
        this.delayMove = delayMove;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void initMoveAnimation() {
        for (int i = 0; i < 4; i++) {
            moveAnimation[i] = new Animation(Animation.INFINITE);
        }
    }

    @Override
    public void update() {
        if (deadAnimation == null) {
            moveAnimation[direction].update();
            img = moveAnimation[direction].getImage();
        } else {
            deadAnimation.update();
            img = deadAnimation.getImage();
            if (deadAnimation.isFinishAnimation()) isDead = true;
        }
        if (!stop) canMove();
    }

    private void canMove() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTimeMove >= delayMove) {
            lastTimeMove = currentTime;
            move();
        }
    }

    protected void move() {
        switch (direction) {
            case UP -> y--;
            case DOWN -> y++;
            case LEFT -> x--;
            case RIGHT -> x++;
        }
    }

    @Override
    public boolean isCollide(Entity e) {
        int dx = 0, dy = 0;
        switch (direction) {
            case UP -> dy = -1;
            case DOWN -> dy = 1;
            case LEFT -> dx = -1;
            case RIGHT -> dx = 1;
        }

        Rectangle rThis = new Rectangle(x + dx, y + dy,
                size, size);
        Rectangle rEntity = new Rectangle(e.getX(), e.getY(),
                size, size);
        return rThis.intersects(rEntity);
    }

    public void detectCollision(List<Entity> entities) {
        for (Entity e : entities) {
            Entity topEntity = e.getTopEntity();
            if (Math.abs(topEntity.getX() - x) > size * 2 || Math.abs(topEntity.getY() - y) > size * 2) continue;
            if (!(topEntity instanceof Grass) && !(topEntity instanceof Item)
                    && isCollide(topEntity)) {
                stop = true;
                return;
            }
        }
    }

    public void detectFlame(List<Bomb> bombs) {
        for (Bomb b : bombs) {
            if (b.isExplode() && b.hitCharacter(this)) {
                stop = true;
                createDeadAnimation();
                return;
            }
        }
    }

    public abstract void createDeadAnimation();
}
