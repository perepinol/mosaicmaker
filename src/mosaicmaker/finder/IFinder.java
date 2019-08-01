package mosaicmaker.finder;

import java.awt.image.BufferedImage;
import java.util.Optional;

public interface IFinder {
    Optional<BufferedImage> findBestFit(int x, int y);
}
