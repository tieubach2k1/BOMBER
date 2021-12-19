package uet.oop.bomberman.entities.mapEntity;

import uet.oop.bomberman.bomb.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;

public class LayerEntity extends Entity {
    private LinkedList<MapEntity> entityList = new LinkedList<>();

    public LayerEntity(int x, int y, MapEntity... entities) {
        size = Sprite.SCALED_SIZE;
        this.x = x * size;
        this.y = y * size;
        entityList.addAll(Arrays.asList(entities));
    }

    @Override
    public void update() {
        this.getTopEntity().update();
    }

    @Override
    public void render(Graphics2D g2d) {
        for (Entity e : entityList) {
            if (!(e instanceof Bomb)) e.render(g2d);
        }
    }

    @Override
    public Entity getTopEntity() {
        return entityList.getLast();
    }

    public void removeTop() {
        entityList.removeLast();
    }

    public void addTop(MapEntity e) {
        entityList.addLast(e);
    }
}
