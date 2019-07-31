package tests;

import mosaicmaker.RGBA;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RGBATest {

    @Test
    public void from_int_works_correctly() {
        int[] initials = new int[]{0xffffffff, 0xff000000, 0xffff0000, 0xff00ff00, 0xff0000ff};
        RGBA[] expected = new RGBA[]{
                new RGBA(255, 255, 255, 1),
                new RGBA(0, 0, 0, 1),
                new RGBA(255, 0, 0, 1),
                new RGBA(0, 255, 0, 1),
                new RGBA(0, 0, 255, 1)
        };
        for (int i = 0; i < initials.length; i++) {
            assertEquals(expected[i], RGBA.fromInt(initials[i]));
        }
    }

    @Test
    public void converting_RGB_int_to_RGBA_and_back_gives_same_value() {
        int initial = -16762712;
        int result = RGBA.fromInt(initial).toInt();
        assertEquals(String.format("Test failed. Expected: %x, got: %x", initial, result), initial, result);
    }

    @Test
    public void averaging_two_colors_gives_back_correct_color() {
        List<RGBA> elements = new ArrayList<>();
        elements.add(new RGBA(255, 255, 0, 1));
        elements.add(new RGBA(255, 0, 255, 0));
        RGBA expected = new RGBA(255, 127, 127, 0.5);
        assertEquals(expected, RGBA.average(elements));
    }
}
