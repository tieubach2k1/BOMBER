package uet.oop.bomberman.item;

import uet.oop.bomberman.entities.mapEntity.MapEntity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Item extends MapEntity {
    public Item(int x, int y) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        size = Sprite.SCALED_SIZE;
    }
}
