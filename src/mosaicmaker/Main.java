package mosaicmaker;

import mosaicmaker.comparators.AverageComparator;
import mosaicmaker.comparators.IComparator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("./red.png")).getSubimage(0, 0, 100, 100);
            IComparator comparator = new AverageComparator(image, 2);
            System.out.println(comparator.compareAtom(image, 0, 0));
            System.out.println(comparator.compareAtom(image, 0, 1));
            System.out.println(comparator.compareAtom(image, 1, 0));
            System.out.println(comparator.compareAtom(image, 1, 1));
        } catch (IOException ex) {
            System.out.println(ex.toString());
            System.exit(1);
        }
    }
}
