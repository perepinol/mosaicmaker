package tests.comparators;

import mosaicmaker.RGBA;
import mosaicmaker.comparators.AverageComparator;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AverageComparatorTest {

    @Test
    public void compare_image_with_its_color_gives_error_zero() {
        try {
            BufferedImage image = ImageIO.read(new File("./pics/red.png")).getSubimage(0, 0, 128, 128);
            AverageComparator comparator = new AverageComparator(image, 16);
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    assertEquals(
                            String.format("(%d, %d)", i, j),
                            0,
                            comparator.compareRGBA(new RGBA(255, 0, 0, 1), i, j));
                }
            }
        } catch (IOException ex) {
            throw new AssertionError("Error reading file");
        }
    }

    @Test
    public void compare_image_with_not_its_color_gives_error() {
        try {
            BufferedImage image = ImageIO.read(new File("./pics/red.png")).getSubimage(0, 0, 128, 128);
            AverageComparator comparator = new AverageComparator(image, 1);
            assertNotEquals(0, comparator.compareRGBA(new RGBA(254, 0, 0, 1), 0, 0));
        } catch (IOException ex) {
            throw new AssertionError("Error reading file");
        }
    }

    @Test
    public void compare_image_with_itself_gives_error_zero() {
        try {
            BufferedImage image = ImageIO.read(new File("./pics/flag.png"));
            AverageComparator comparator = new AverageComparator(image, 1);
            assertEquals(0, comparator.compareAtom(image, 0, 0));
        } catch (IOException ex) {
            throw new AssertionError("Error reading file");
        }
    }

    @Test
    public void one_color_picture_gives_error_zero_when_compared_with_that_color() {
        try {
            BufferedImage image = ImageIO.read(new File("./pics/red.png")).getSubimage(0, 0, 128, 128);
            BufferedImage red = image.getSubimage(0, 0, 16, 16);
            AverageComparator comparator = new AverageComparator(image, 1);
            assertEquals(0, comparator.compareAtom(red, 0, 0));
        } catch (IOException ex) {
            throw new AssertionError("Error reading file");
        }
    }

    @Test
    public void one_color_picture_32_atoms_give_error_zero_when_compared_with_that_color() {
        try {
            BufferedImage image = ImageIO.read(new File("./pics/red.png")).getSubimage(0, 0, 128, 128);
            BufferedImage red = image.getSubimage(0, 0, 16, 16);
            AverageComparator comparator = new AverageComparator(image, 32);
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 32; j++) {
                    assertEquals(String.format("(%d, %d)", i, j), 0, comparator.compareAtom(red, i, j));
                }
            }
        } catch (IOException ex) {
            throw new AssertionError("Error reading file");
        }
    }
}
