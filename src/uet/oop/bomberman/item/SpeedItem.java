package uet.oop.bomberman.item;

import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item {
    public SpeedItem(int x, int y) {
        super(x, y);
        img = Sprite.powerup_speed.getImage();
    }

    @Override
    public void update() {

    }
}
