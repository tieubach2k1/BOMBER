package uet.oop.bomberman.item;

import uet.oop.bomberman.character.enemy.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Item {
    public Portal(int x, int y) {
        super(x, y);
        img = Sprite.portal.getImage();
    }

    @Override
    public boolean isCollide(Entity e) {
        if (Enemy.numEnemies > 0) return false;
        return super.isCollide(e);
    }

    @Override
    public void update() {

    }
}
