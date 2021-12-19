package uet.oop.bomberman.character.enemy.smartEnemy;

import uet.oop.bomberman.animation.Animation;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends SmartEnemy {

    public Kondoria(int x, int y) {
        super(x, y);
        img = Sprite.kondoria_right1.getImage();
        delayMove = 15;
    }

    @Override
    public void initMoveAnimation() {
        super.initMoveAnimation();

        moveAnimation[UP].addImage(Sprite.kondoria_left1.getImage());
        moveAnimation[UP].addImage(Sprite.kondoria_left2.getImage());
        moveAnimation[UP].addImage(Sprite.kondoria_left3.getImage());
        moveAnimation[UP].setTimeDelay(100);

        moveAnimation[DOWN].addImage(Sprite.kondoria_right1.getImage());
        moveAnimation[DOWN].addImage(Sprite.kondoria_right2.getImage());
        moveAnimation[DOWN].addImage(Sprite.kondoria_right3.getImage());
        moveAnimation[DOWN].setTimeDelay(100);

        moveAnimation[LEFT].addImage(Sprite.kondoria_left1.getImage());
        moveAnimation[LEFT].addImage(Sprite.kondoria_left2.getImage());
        moveAnimation[LEFT].addImage(Sprite.kondoria_left3.getImage());
        moveAnimation[LEFT].setTimeDelay(100);

        moveAnimation[RIGHT].addImage(Sprite.kondoria_right1.getImage());
        moveAnimation[RIGHT].addImage(Sprite.kondoria_right2.getImage());
        moveAnimation[RIGHT].addImage(Sprite.kondoria_right3.getImage());
        moveAnimation[RIGHT].setTimeDelay(100);
    }

    @Override
    public void createDeadAnimation() {
        deadAnimation = new Animation(Animation.ONE_CYCLE);
        deadAnimation.addImage(Sprite.kondoria_dead.getImage());
        deadAnimation.setTimeDelay(500);
    }
}

