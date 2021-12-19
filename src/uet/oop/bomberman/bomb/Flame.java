package uet.oop.bomberman.bomb;

import uet.oop.bomberman.animation.Animation;
import uet.oop.bomberman.character.Character;
import uet.oop.bomberman.entities.IRender;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Flame implements IRender {
    private final List<FlameSegment> flames = new ArrayList<>();

    public Flame(int x, int y, int direction, int length, int delayTime) {
        int dx = 0, dy = 0;
        switch (direction) {
            case Animation.UP -> dy = -1;
            case Animation.DOWN -> dy = 1;
            case Animation.LEFT -> dx = -1;
            case Animation.RIGHT -> dx = 1;
        }
        for (int i = 0; i < length; i++) {
            boolean last = (i == length - 1);
            flames.add(new FlameSegment(x + dx * i, y + dy * i, direction, last, delayTime)
            );
        }
    }

    @Override
    public void update() {
        for (FlameSegment fs : flames) {
            fs.update();
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        for (FlameSegment fs : flames) {
            fs.render(g2d);
        }
    }

    public boolean hitCharacter(Character e) {
        for (FlameSegment flameSegment : flames) {
            if (flameSegment.isCollide(e)) return true;
        }
        return false;
    }
}
