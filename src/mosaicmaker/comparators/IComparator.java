package mosaicmaker.comparators;

import java.awt.image.BufferedImage;

public interface IComparator {
    int compareAtom(BufferedImage other, int x, int y);
}
