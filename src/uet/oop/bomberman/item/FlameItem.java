package uet.oop.bomberman.item;

import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {
    public FlameItem(int x, int y) {
        super(x, y);
        img = Sprite.powerup_flames.getImage();
    }

    @Override
    public void update() {

    }
}
