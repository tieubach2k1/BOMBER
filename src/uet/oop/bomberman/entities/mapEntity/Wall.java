package uet.oop.bomberman.entities.mapEntity;

import uet.oop.bomberman.graphics.Sprite;

public class Wall extends MapEntity {

    public Wall(int x, int y) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        img = Sprite.wall.getImage();
        size = Sprite.SCALED_SIZE;
    }

    @Override
    public void update() {

    }
}
