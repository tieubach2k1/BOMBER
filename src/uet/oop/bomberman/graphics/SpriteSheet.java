package uet.oop.bomberman.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Tất cả sprite (hình ảnh game) được lưu trữ vào một ảnh duy nhất
 * Class này giúp lấy ra các sprite riêng từ 1 ảnh chung duy nhất đó
 */
public class SpriteSheet {

    private String _path;
    public final int SIZE;
    private final int TRANSPARENT_COLOR = 0xffff00ff;
    public BufferedImage image;

    public static SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256);


    public SpriteSheet(String path, int size) {
        _path = path;
        SIZE = size;
        load();
    }

    private void load() {
        Image tmpImg = new ImageIcon(getClass().getResource(_path)).getImage();
        image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(tmpImg, 0, 0, null);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (image.getRGB(i, j) == TRANSPARENT_COLOR) {
                    int color = image.getRGB(i, j);
                    int mc = 0x00ffffff;
                    int newColor = color & mc;
                    image.setRGB(i, j, newColor);
                }
            }
        }
    }
}
