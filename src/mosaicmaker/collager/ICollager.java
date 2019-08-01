package mosaicmaker.collager;

import java.awt.image.BufferedImage;

public interface ICollager {
    void draw(BufferedImage image, int x, int y);
    BufferedImage finish();
}
