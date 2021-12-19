package uet.oop.bomberman.animation;

import uet.oop.bomberman.entities.IRender;
 
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Animation implements IRender {
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public static final int INFINITE = 0;
    public static final int INFINITE_WITH_RESTRICT_TIME = 1;
    public static final int ONE_CYCLE = 2;

    private final int loopType;
    private final List<Image> imageList = new ArrayList<>();
    private final long timeStart;
    private int imageIndex;
    private int timeDelay;
    private long lastTimeRender;
    private long timeRender;
    private boolean finishAnimation;

    public Animation(int loopType) {
        this.loopType = loopType;
        timeStart = System.currentTimeMillis();
    }

    public boolean isFinishAnimation() {
        return finishAnimation;
    }

    public void setTimeRender(long timeRender) {
        this.timeRender = timeRender;
    }

    public int getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(int timeDelay) {
        this.timeDelay = timeDelay;
    }

    public void addImage(Image image) {
        imageList.add(image);
    }

    public Image getImage() {
        return imageList.get(imageIndex);
    }

    @Override
    public void render(Graphics2D g2d) {

    }

    @Override
    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTimeRender > timeDelay) {
            lastTimeRender = currentTime;
            updateImageIndex();
        }
        if (loopType == INFINITE_WITH_RESTRICT_TIME
                && currentTime - timeStart > timeRender) {
            finishAnimation = true;
        }
    }

    private void updateImageIndex() {
        switch (loopType) {
            case INFINITE, INFINITE_WITH_RESTRICT_TIME -> imageIndex = (imageIndex + 1) % imageList.size();
            case ONE_CYCLE -> {
                imageIndex = (imageIndex + 1) % imageList.size();
                if (imageIndex == 0) finishAnimation = true;
            }
        }
    }
}
