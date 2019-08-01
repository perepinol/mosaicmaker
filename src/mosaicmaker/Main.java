package mosaicmaker;

import mosaicmaker.collager.GenericCollager;
import mosaicmaker.collager.ICollager;
import mosaicmaker.comparators.AverageComparator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("./pics/red.png"));
            ICollager collager = new GenericCollager("./pics", new AverageComparator(image, 1));
            Optional<BufferedImage> result = collager.findBestFit(0, 0);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            System.exit(1);
        }
    }
}
