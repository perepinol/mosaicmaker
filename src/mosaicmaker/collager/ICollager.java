package mosaicmaker.collager;

import java.awt.image.BufferedImage;
import java.util.Optional;

public interface ICollager {
    Optional<BufferedImage> findBestFit(int x, int y);
}
