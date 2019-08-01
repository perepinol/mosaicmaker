package mosaicmaker;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class RGBA {
    private final int alpha;
    private final int red;
    private final int green;
    private final int blue;

    public RGBA(int r, int g, int b, double a) {
        a = a > 1 ? 1 : a < 0 ? 0 : a;
        alpha = (int) (a * 255);
        red = r > 255 ? 255 : r < 0 ? 0 : r;
        green = g > 255 ? 255 : g < 0 ? 0 : g;
        blue = b > 255 ? 255 : b < 0 ? 0 : b;
    }

    private RGBA(int[] rgba) {
        this(rgba[0], rgba[1], rgba[2], ((double) rgba[3]) / 255);
    }

    public static RGBA fromInt(Integer rgba) {
        int byteValue = rgba;
        int b = byteValue & 0xff;
        int g = (byteValue >> 8) & 0xff;
        int r = (byteValue >> 16) & 0xff;
        int a = (byteValue >> 24) & 0xff;
        return new RGBA(r, g, b, a);
    }

    public int toInt() {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public double getAlpha() {
        return ((double) alpha) / 255;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public RGBA operate(RGBA other, BinaryOperator<Integer> operation) {
        return new RGBA(operateWithoutConstraints(other, operation));
    }

    public int[] operateWithoutConstraints(RGBA other, BinaryOperator<Integer> operation) {
        return new int[]{
                operation.apply(red, other.red),
                operation.apply(green, other.green),
                operation.apply(blue, other.blue),
                operation.apply(alpha, other.alpha)
        };
    }

    public static RGBA average(List<RGBA> lst) {
        int[] accumulated = lst.stream()
                .map(rgba -> new int[]{
                            rgba.red,
                            rgba.green,
                            rgba.blue,
                            rgba.alpha
                })
                .reduce(new int[]{0, 0, 0, 0}, (accum, array) -> {
                    for (int i = 0; i < accum.length; i++) {
                        accum[i] += array[i];
                    }
                    return accum;
                });
        for (int i = 0; i < accumulated.length; i++) {
            accumulated[i] /= lst.size();
        }
        return new RGBA(accumulated);
    }

    public static RGBA averageInt(List<Integer> lst) {
        return RGBA.average(lst.stream().map(RGBA::fromInt).collect(Collectors.toList()));
    }

    public boolean equals(Object o) {
        if (!(o instanceof RGBA)) return false;
        RGBA other = (RGBA) o;
        return red == other.red && green == other.green && blue == other.blue && alpha == other.alpha;
    }

    public String toString() {
        return String.format("R%dG%dB%dA%f", red, green, blue, getAlpha());
    }
}
