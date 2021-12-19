package uet.oop.bomberman.character.enemy;

import uet.oop.bomberman.character.Character;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends Character {
    public static int numEnemies = 0;
    protected List<Entity> entities = new ArrayList<>();

    public int getDirection() {
        return this.direction;
    }

    public Enemy(int x, int y) {
        super(x, y);
        delayMove = 30;
        size = Sprite.SCALED_SIZE;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }


    @Override
    public void update() {
        super.update();
        if (deadAnimation != null) return;
        if (stop || (x % size == 0 && y % size == 0)) {
            chooseDirection();
        }

    }

    public void chooseDirection() {
        setDirection((int) Math.floor(Math.random() * 4));
        stop = false;
    }
}
