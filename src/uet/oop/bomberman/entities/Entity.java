package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public abstract class Entity implements IRender {
    protected int x;
    protected int y;
    protected Image img;
    protected int size = Sprite.SCALED_SIZE;
    protected boolean isRemove;

    public boolean isRemove() {
        return isRemove;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(img, x, y, size, size, null);
    }

    @Override
    public abstract void update();

    public boolean isCollide(Entity e) {
        e = e.getTopEntity();
        Rectangle rThis = new Rectangle(x, y, size, size);
        Rectangle rThat = new Rectangle(e.x, e.y, e.size, e.size);
        return rThis.intersects(rThat);
    }

    public Entity getTopEntity() {
        return this;
    }
}
