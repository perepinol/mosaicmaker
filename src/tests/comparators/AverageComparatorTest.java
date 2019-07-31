package tests.comparators;

import mosaicmaker.comparators.AverageComparator;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AverageComparatorTest {

    @Test
    public void compare_image_with_itself_gives_error_zero() {
        try {
            BufferedImage image = ImageIO.read(new File("./red.png")).getSubimage(0, 0, 100, 100);
            AverageComparator comparator = new AverageComparator(image, 1);
            assertEquals(0, comparator.compareAtom(image, 0, 0));
        } catch (IOException ex) {
            throw new AssertionError("Error reading file");
        }
    }
}
