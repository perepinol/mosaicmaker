package mosaicmaker.comparator;

import mosaicmaker.RGBA;

import java.awt.image.BufferedImage;

public interface IComparator {
    RGBA getRGBA(int x, int y);
    int compareAtom(BufferedImage other, int x, int y);
    int compareRGBA(RGBA other, int x, int y);
}
