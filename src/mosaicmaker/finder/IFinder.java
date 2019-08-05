package mosaicmaker.finder;

import java.awt.image.BufferedImage;
import java.util.Optional;

public interface IFinder {
    Optional<String> findBestFit(int x, int y);
    Optional<BufferedImage> getBestFit(int x, int y);
}
