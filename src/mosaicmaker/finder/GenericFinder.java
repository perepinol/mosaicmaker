package mosaicmaker.finder;

import mosaicmaker.RGBA;
import mosaicmaker.comparator.IComparator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GenericFinder implements IFinder {

    private IComparator comparator;
    private List<Pair<RGBA, String>> imageRGBAs;

    public GenericFinder(String path, IComparator comparator) {
        this.comparator = comparator;
        imageRGBAs = findAllAverages(path);
    }

    @Override
    public Optional<BufferedImage> getBestFit(int x, int y) {
        return findBestFit(x, y)
                .map(name -> {
                    try {
                        return ImageIO.read(new File(name));
                    } catch (IOException ex) {
                        System.out.println(ex.toString());
                        return null;
                    }
                });
    }

    @Override
    public Optional<String> findBestFit(int x, int y) {
        return imageRGBAs.stream().map(pair -> new Pair<>(
                comparator.compareRGBA(pair.getKey(), x, y),
                pair.getValue()
        ))
                .min(Comparator.comparingInt(Pair::getKey))
                .map(Pair::getValue);
    }

    private List<Pair<RGBA, String>> findAllAverages(String path) {
        File dir = new File(path);
        if (!dir.exists()) throw new IllegalArgumentException("Directory does not exist");
        File[] files = dir.listFiles();
        if (files == null) throw new IllegalArgumentException("Path is not a directory");

        List<Pair<RGBA, String>> imageRGBAs = new ArrayList<>();
        BufferedImage image;
        for (File file : files) {
            try {
                image = ImageIO.read(file);
                int[] colors = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
                RGBA average = RGBA.averageInt(Arrays.stream(colors).boxed().collect(Collectors.toList()));
                imageRGBAs.add(new Pair<>(average, file.getPath()));
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        }

        imageRGBAs.sort((firstPair, secondPair) -> { // Sort by red, then green, then blue, then alpha
            int[] subtracted = firstPair.getKey().operateWithoutConstraints(secondPair.getKey(), (a, b) -> a - b);
            return Arrays.stream(subtracted)
                    .filter(value -> value != 0)
                    .findFirst()
                    .orElse(0); // Return first non-zero, or zero if all are zero
        });
        return imageRGBAs;
    }

    private static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
