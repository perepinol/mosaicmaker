package mosaicmaker.comparator;

import mosaicmaker.RGBA;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AverageComparator implements IComparator {

    private BufferedImage image;
    private int atomWidth;
    private int atomHeight;

    public AverageComparator(BufferedImage image, int numAtoms) {
        if (image.getWidth() == 0 || image.getHeight() == 0) throw new IllegalArgumentException("Image does not have pixels");
        Double atomWidth = (double) image.getWidth() / numAtoms;
        Double atomHeight = (double) image.getHeight() / numAtoms;
        if (atomWidth.intValue() != atomWidth || atomHeight.intValue() != atomHeight) throw new IllegalArgumentException("Image cannot be divided in this number of atoms");
        this.image = image;
        this.atomWidth = image.getWidth() / numAtoms;
        this.atomHeight = image.getHeight() / numAtoms;
    }

    @Override
    public RGBA getRGBA(int x, int y) {
        if (x * atomWidth > image.getWidth() || y * atomHeight > image.getHeight())
            throw new IndexOutOfBoundsException("Coordinate out of bounds");
        int[] thisRGB = image.getRGB(x * atomWidth, y * atomHeight, atomWidth, atomHeight, null, 0, atomWidth);
        return RGBA.averageInt(Arrays.stream(thisRGB).boxed().collect(Collectors.toList()));
    }

    @Override
    public int compareRGBA(RGBA other, int x, int y) {
        RGBA thisAverage = getRGBA(x, y);
        RGBA error = thisAverage.operate(other, (a, b) -> Math.abs(a - b));
        return error.getRed() + error.getGreen() + error.getBlue() + (int) (error.getAlpha() * 255);
    }

    @Override
    public int compareAtom(BufferedImage other, int x, int y) {
        if (x * atomWidth > image.getWidth() || y * atomHeight > image.getHeight())
            throw new IndexOutOfBoundsException("Coordinate out of bounds");
        if ((double) image.getWidth() / image.getHeight() != (double) other.getWidth() / other.getHeight()) return -1;

        int[] otherRGB = other.getRGB(0, 0, other.getWidth(), other.getHeight(), null, 0, other.getWidth());
        RGBA otherAverage = RGBA.averageInt(Arrays.stream(otherRGB).boxed().collect(Collectors.toList()));

        return compareRGBA(otherAverage, x, y);
    }
}
