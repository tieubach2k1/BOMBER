package uet.oop.bomberman.bomb;

import uet.oop.bomberman.animation.Animation;
import uet.oop.bomberman.character.Character;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mapEntity.Brick;
import uet.oop.bomberman.entities.mapEntity.MapEntity;
import uet.oop.bomberman.entities.mapEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.play.PlayManager;
import uet.oop.bomberman.sound.SoundManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bomb extends MapEntity {
    private final Animation waitingAnimation
            = new Animation(Animation.INFINITE_WITH_RESTRICT_TIME);
    private final int[] flameLength = new int[4];
    private boolean isExplode;

    private final List<Entity> obstacleList = new ArrayList<>();
    private final int maxLength;

    private boolean allowToPass = true;

    private final Flame[] flames = new Flame[4];
    private Animation explodeAnimation;
    private List<Entity> mapEntityList;

    private final SoundManager bombExplodeSound = new SoundManager("/audios/boom.wav");

    public void setMapEntityList(List<Entity> mapEntityList) {
        this.mapEntityList = mapEntityList;
    }

    public Bomb(int x, int y, int maxLength) {
        size = Sprite.SCALED_SIZE;
        this.x = x * size;
        this.y = y * size;
        this.maxLength = maxLength;
        img = Sprite.bomb.getImage();
        createAnimation();
    }

    public boolean isExplode() {
        return isExplode;
    }

    public boolean isAllowToPass() {
        return allowToPass;
    }

    public void setAllowToPass(boolean allowToPass) {
        this.allowToPass = allowToPass;
    }

    private void createAnimation() {
        waitingAnimation.setTimeDelay(200);
        waitingAnimation.setTimeRender(2000);
        waitingAnimation.addImage(Sprite.bomb.getImage());
        waitingAnimation.addImage(Sprite.bomb_1.getImage());
        waitingAnimation.addImage(Sprite.bomb_2.getImage());
    }

    public void calculateLength(List<Entity> entities) {
        int xBomb = x / Sprite.SCALED_SIZE;
        int yBomb = y / Sprite.SCALED_SIZE;
        for (int direction = 0; direction < 4; direction++) {
            flameLength[direction] = maxLength;
            int dx = 0, dy = 0;
            switch (direction) {
                case Animation.UP -> dy = -1;
                case Animation.DOWN -> dy = 1;
                case Animation.LEFT -> dx = -1;
                case Animation.RIGHT -> dx = 1;
            }
            for (int i = 1; i <= maxLength; i++) {
                int xEntity = xBomb + i * dx;
                int yEntity = yBomb + i * dy;
                Entity e = entities.get(xEntity + PlayManager.MAP_WIDTH * yEntity);
                Entity topEntity = e.getTopEntity();
                if (topEntity instanceof Brick || topEntity instanceof Wall
                        || (topEntity instanceof Bomb && !((Bomb) topEntity).isExplode)) {
                    if (e instanceof Wall) {
                        flameLength[direction] = i - 1;
                    } else {
                        flameLength[direction] = i;
                        obstacleList.add(topEntity);
                    }
                    break;
                }
            }
        }
    }

    public boolean hitCharacter(Character c) {
        if (isCollide(c)) return true;
        for (int i = 0; i < 4; i++) {
            if (flames[i].hitCharacter(c)) return true;
        }
        return false;
    }

    @Override
    public void render(Graphics2D g2d) {
        img = Objects
                .requireNonNullElse(explodeAnimation, waitingAnimation)
                .getImage();
        super.render(g2d);
        if (!isRemove) {
            for (int i = 0; i < 4; i++) {
                if (flames[i] != null) flames[i].render(g2d);
            }
        }
    }

    @Override
    public void update() {
        if (explodeAnimation == null) {
            waitingAnimation.update();
            if (waitingAnimation.isFinishAnimation()) {
                explodeBomb();
            }
        } else {
            explodeAnimation.update();
            for (int i = 0; i < 4; i++) {
                flames[i].update();
            }
            isRemove = explodeAnimation.isFinishAnimation();
        }
    }

    public void explodeBomb() {
        bombExplodeSound.play();
        changeCenterAnimation();
        calculateLength(mapEntityList);
        createFlame();
        explodeObstacle();
    }

    private void explodeObstacle() {
        for (Entity e : obstacleList) {
            if (e instanceof Brick) ((Brick) e).createExplodeAnimation();
            if (e instanceof Bomb) {
                ((Bomb) e).explodeBomb();
            }
        }
    }


    private void changeCenterAnimation() {
        explodeAnimation = new Animation(Animation.ONE_CYCLE);
        explodeAnimation.setTimeDelay(200);
        explodeAnimation.addImage(Sprite.bomb_exploded.getImage());
        explodeAnimation.addImage(Sprite.bomb_exploded1.getImage());
        explodeAnimation.addImage(Sprite.bomb_exploded2.getImage());
        isExplode = true;
    }

    private void createFlame() {
        for (int direction = 0; direction < 4; direction++) {
            int xFlame = x, yFlame = y;
            switch (direction) {
                case Animation.UP -> yFlame -= size;
                case Animation.DOWN -> yFlame += size;
                case Animation.LEFT -> xFlame -= size;
                case Animation.RIGHT -> xFlame += size;
            }
            flames[direction] = new Flame(xFlame / size, yFlame / size, direction,
                    flameLength[direction], explodeAnimation.getTimeDelay());
        }
    }
}
