package uet.oop.bomberman.item;

import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Item {
    public BombItem(int x, int y) {
        super(x, y);
        img = Sprite.powerup_bombs.getImage();
    }

    @Override
    public void update() {

    }
}
