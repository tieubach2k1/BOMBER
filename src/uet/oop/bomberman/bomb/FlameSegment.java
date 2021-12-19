package uet.oop.bomberman.bomb;

import uet.oop.bomberman.animation.Animation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class FlameSegment extends Entity {
    private final int direction;
    private final boolean last;
    private final Animation explodeAnimation = new Animation(Animation.ONE_CYCLE);

    public FlameSegment(int x, int y, int direction, boolean last, int delayTime) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.direction = direction;
        this.last = last;
        size = Sprite.SCALED_SIZE;
        findImage();
        img = explodeAnimation.getImage();
        explodeAnimation.setTimeDelay(delayTime);
    }

    private void findImage() {
        switch (direction) {
            case Animation.UP -> {
                if (last) {
                    explodeAnimation.addImage(Sprite.explosion_vertical_top_last.getImage());
                    explodeAnimation.addImage(Sprite.explosion_vertical_top_last1.getImage());
                    explodeAnimation.addImage(Sprite.explosion_vertical_top_last2.getImage());
                } else {
                    explodeAnimation.addImage(Sprite.explosion_vertical.getImage());
                    explodeAnimation.addImage(Sprite.explosion_vertical1.getImage());
                    explodeAnimation.addImage(Sprite.explosion_vertical2.getImage());
                }
            }
            case Animation.DOWN -> {
                if (last) {
                    explodeAnimation.addImage(Sprite.explosion_vertical_down_last.getImage());
                    explodeAnimation.addImage(Sprite.explosion_vertical_down_last1.getImage());
                    explodeAnimation.addImage(Sprite.explosion_vertical_down_last2.getImage());
                } else {
                    explodeAnimation.addImage(Sprite.explosion_vertical.getImage());
                    explodeAnimation.addImage(Sprite.explosion_vertical1.getImage());
                    explodeAnimation.addImage(Sprite.explosion_vertical2.getImage());
                }
            }
            case Animation.LEFT -> {
                if (last) {
                    explodeAnimation.addImage(Sprite.explosion_horizontal_left_last.getImage());
                    explodeAnimation.addImage(Sprite.explosion_horizontal_left_last1.getImage());
                    explodeAnimation.addImage(Sprite.explosion_horizontal_left_last2.getImage());
                } else {
                    explodeAnimation.addImage(Sprite.explosion_horizontal.getImage());
                    explodeAnimation.addImage(Sprite.explosion_horizontal1.getImage());
                    explodeAnimation.addImage(Sprite.explosion_horizontal2.getImage());
                }
            }
            case Animation.RIGHT -> {
                if (last) {
                    explodeAnimation.addImage(Sprite.explosion_horizontal_right_last.getImage());
                    explodeAnimation.addImage(Sprite.explosion_horizontal_right_last1.getImage());
                    explodeAnimation.addImage(Sprite.explosion_horizontal_right_last2.getImage());
                } else {
                    explodeAnimation.addImage(Sprite.explosion_horizontal.getImage());
                    explodeAnimation.addImage(Sprite.explosion_horizontal1.getImage());
                    explodeAnimation.addImage(Sprite.explosion_horizontal2.getImage());
                }
            }
        }
    }

    @Override
    public void update() {
        explodeAnimation.update();
        img = explodeAnimation.getImage();
    }


}
