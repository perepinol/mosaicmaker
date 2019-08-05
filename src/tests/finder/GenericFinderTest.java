package tests.finder;

import mosaicmaker.comparator.AverageComparator;
import mosaicmaker.finder.GenericFinder;
import mosaicmaker.finder.IFinder;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public class GenericFinderTest {

    @Test
    public void finds_same_picture_in_a_folder() {
        try {
            File file = new File("./pics/red.png");
            IFinder finder = new GenericFinder("./pics", new AverageComparator(ImageIO.read(file), 1));
            Optional<String> result = finder.findBestFit(0, 0);
            assertFalse(result.isEmpty());
            assertEquals(file, new File(result.get()));
        } catch (IOException ex) {
            throw new AssertionError("Error reading file");
        }
    }
}
