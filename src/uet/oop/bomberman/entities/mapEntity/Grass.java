package uet.oop.bomberman.entities.mapEntity;

import uet.oop.bomberman.graphics.Sprite;

public class Grass extends MapEntity {

    public Grass(int x, int y) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        img = Sprite.grass.getImage();
        size = Sprite.SCALED_SIZE;
    }

    @Override
    public void update() {

    }
}
