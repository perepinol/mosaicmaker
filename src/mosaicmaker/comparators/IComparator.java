package mosaicmaker.comparators;

import mosaicmaker.RGBA;

import java.awt.image.BufferedImage;

public interface IComparator {
    int compareAtom(BufferedImage other, int x, int y);
    int compareRGBA(RGBA other, int x, int y);
}
