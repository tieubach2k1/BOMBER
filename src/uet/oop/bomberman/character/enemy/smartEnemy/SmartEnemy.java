package uet.oop.bomberman.character.enemy.smartEnemy;

import uet.oop.bomberman.character.Bomber;
import uet.oop.bomberman.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.play.PlayManager;

public class SmartEnemy extends Enemy {
    protected Bomber bomber;

    public void setBomber(Bomber bomber) {
        this.bomber = bomber;
    }

    public SmartEnemy(int x, int y) {
        super(x, y);
    }

    @Override
    public void initMoveAnimation() {
        super.initMoveAnimation();
    }

    @Override
    public void createDeadAnimation() {
        
    }

    @Override
    public void chooseDirection() {
        int vX = x / Sprite.SCALED_SIZE;
        int vY = y / Sprite.SCALED_SIZE;
        int dir = bomber.getTraceBack()[vX + PlayManager.MAP_WIDTH * vY];
        if (dir != -1) {
            setDirection(dir);
        } else setDirection((int) Math.floor(Math.random() * 4));
        stop = false;
    }
}
