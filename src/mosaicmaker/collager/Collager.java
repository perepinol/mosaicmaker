package mosaicmaker.collager;

import mosaicmaker.RGBA;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Collager implements ICollager {

    private BufferedImage drawing;
    private Graphics2D graphics;
    private int atomWidth;
    private int atomHeight;

    public Collager(int width, int height, int numAtoms) {
        Double atomWidth = (double) width / numAtoms;
        Double atomHeight = (double) height / numAtoms;
        if (width <= 0 || height <= 0 ||
                atomWidth.intValue() != atomWidth || atomHeight.intValue() != atomHeight)
            throw new IllegalArgumentException("Image cannot be divided in this number of atoms");
        drawing = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics = drawing.createGraphics();
        this.atomWidth = atomWidth.intValue();
        this.atomHeight = atomHeight.intValue();
    }

    @Override
    public void draw(BufferedImage image, int x, int y) {
        graphics.drawImage(image, x * atomWidth, y * atomHeight, null);
    }

    @Override
    public void drawColor(RGBA color, int x, int y) {
        Color initialColor = graphics.getColor();
        graphics.setColor(color.toColor());
        graphics.fill(new Rectangle(x * atomWidth, y * atomHeight, atomWidth, atomHeight));
        graphics.setColor(initialColor);
    }

    @Override
    public BufferedImage finish() {
        graphics.dispose();
        return drawing;
    }
}
