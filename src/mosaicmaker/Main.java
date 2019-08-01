package mosaicmaker;

import mosaicmaker.collager.Collager;
import mosaicmaker.collager.ICollager;
import mosaicmaker.finder.GenericFinder;
import mosaicmaker.finder.IFinder;
import mosaicmaker.comparators.AverageComparator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        int width = 1674;
        int height = 1026;
        int numAtoms = 54;

        try {
            System.out.println("Reading image...");
            BufferedImage image = ImageIO.read(new File("./pic.jpeg")).getSubimage(0, 0, width, height);
            System.out.println("Image read. Processing available pictures...");
            IFinder finder = new GenericFinder("./pics", new AverageComparator(image, numAtoms));
            System.out.println("Done. Starting collage...");
            ICollager collager = new Collager(image.getWidth(), image.getHeight(), numAtoms);

            for (int i = 0; i < numAtoms; i++) {
                for (int j = 0; j < numAtoms; j++) {
                    Optional<BufferedImage> bestFit = finder.findBestFit(i, j);
                    if (bestFit.isEmpty()) {
                        collager.draw(image, i, j);
                    } else {
                        collager.draw(bestFit.get(), i, j);
                    }
                    System.out.print(getProgressFormat(i * numAtoms + j, numAtoms * numAtoms));
                }
            }

            System.out.print(getProgressFormat(1, 1));

            ImageIO.write(collager.finish(), "png", new File("result.png"));
            System.out.println("Collage finished. Exiting...");

        } catch (IOException ex) {
            System.out.println(ex.toString());
            System.exit(1);
        }
    }

    private static String getProgressFormat(int current, int total) {
        int progress = current * 100 / total;
        return String.format(
                "\rProgress: |%s%s| %d%% %s",
                "-".repeat(progress / 2),
                " ".repeat(50 - progress / 2),
                progress,
                current == total ? "\n" : ""
        );
    }

    private static void resizeImages(String path, int width, int height) throws IOException {
        File folder = new File(path);
        File[] files = folder.listFiles();
        if (files == null) return;
        for (File file : files) {
            Image image = ImageIO.read(file).getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
            ImageIO.write(toBufferedImage(image), "png", file);
        }
    }

    private static BufferedImage toBufferedImage(Image image) {
        BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bimage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return bimage;
    }
}
