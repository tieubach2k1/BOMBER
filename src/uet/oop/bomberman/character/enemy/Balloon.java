package uet.oop.bomberman.character.enemy;

import uet.oop.bomberman.animation.Animation;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    public Balloon(int x, int y) {
        super(x, y);
    }

    @Override
    public void initMoveAnimation() {
        super.initMoveAnimation();

        moveAnimation[UP].addImage(Sprite.balloom_left1.getImage());
        moveAnimation[UP].addImage(Sprite.balloom_left2.getImage());
        moveAnimation[UP].addImage(Sprite.balloom_left3.getImage());
        moveAnimation[UP].setTimeDelay(100);

        moveAnimation[DOWN].addImage(Sprite.balloom_right1.getImage());
        moveAnimation[DOWN].addImage(Sprite.balloom_right2.getImage());
        moveAnimation[DOWN].addImage(Sprite.balloom_right3.getImage());
        moveAnimation[DOWN].setTimeDelay(100);

        moveAnimation[LEFT].addImage(Sprite.balloom_left1.getImage());
        moveAnimation[LEFT].addImage(Sprite.balloom_left2.getImage());
        moveAnimation[LEFT].addImage(Sprite.balloom_left3.getImage());
        moveAnimation[LEFT].setTimeDelay(100);

        moveAnimation[RIGHT].addImage(Sprite.balloom_right1.getImage());
        moveAnimation[RIGHT].addImage(Sprite.balloom_right2.getImage());
        moveAnimation[RIGHT].addImage(Sprite.balloom_right3.getImage());
        moveAnimation[RIGHT].setTimeDelay(100);
    }

    @Override
    public void createDeadAnimation() {
        deadAnimation = new Animation(Animation.ONE_CYCLE);
        deadAnimation.addImage(Sprite.balloom_dead.getImage());
        deadAnimation.addImage(Sprite.mob_dead1.getImage());
        deadAnimation.addImage(Sprite.mob_dead2.getImage());
        deadAnimation.addImage(Sprite.mob_dead3.getImage());
        deadAnimation.setTimeDelay(300);
    }
}
