package uet.oop.bomberman.entities.mapEntity;

import uet.oop.bomberman.animation.Animation;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends MapEntity {
    private Animation explodeAnimation;

    public Brick(int x, int y) {
        img = Sprite.brick.getImage();
        size = Sprite.SCALED_SIZE;
        this.x = x * size;
        this.y = y * size;
    }

    public void createExplodeAnimation() {
        explodeAnimation = new Animation(Animation.ONE_CYCLE);
        explodeAnimation.addImage(Sprite.brick_exploded.getImage());
        explodeAnimation.addImage(Sprite.brick_exploded1.getImage());
        explodeAnimation.addImage(Sprite.brick_exploded2.getImage());

        explodeAnimation.setTimeDelay(500);
    }

    @Override
    public void update() {
        if (explodeAnimation != null) {
            explodeAnimation.update();
            img = explodeAnimation.getImage();
            isRemove = explodeAnimation.isFinishAnimation();
        }
    }
}
