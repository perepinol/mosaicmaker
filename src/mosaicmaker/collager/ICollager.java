package mosaicmaker.collager;

import mosaicmaker.RGBA;

import java.awt.image.BufferedImage;

public interface ICollager {
    void draw(BufferedImage image, int x, int y);
    void drawColor(RGBA color, int x, int y);
    BufferedImage finish();
}
