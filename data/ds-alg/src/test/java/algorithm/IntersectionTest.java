package algorithm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class IntersectionTest {

    @ParameterizedTest
    @MethodSource("sortedArrayIntersectionDataSet")
    void sortedArrayIntersection(int[] first, int[] second, int[] expected) {
        assertArrayEquals(expected, Intersection.forSortedArray(first, second));
    }

    @Test
    void unsortedArrayIntersection() {
        assertArrayEquals(new int[]{2}, Intersection.forUnsortedIntersection(new int[]{1, 2, 2, 1}, new int[]{2, 2}));
        assertArrayEquals(new int[]{9, 4}, Intersection.forUnsortedIntersection(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4}));
    }

    static Stream<Arguments> sortedArrayIntersectionDataSet() {
        return Stream.of(
                arguments(new int[]{1, 3, 4, 6, 7, 9}, new int[]{1, 2, 4, 5, 9, 10}, new int[]{1, 4, 9}),
                arguments(new int[]{1, 2, 9, 10, 11, 12}, new int[]{0, 1, 2, 3, 4, 5, 8, 9, 10, 12, 14, 15}, new int[]{1, 2, 9, 10, 12}),
                arguments(new int[]{0, 1, 2, 3, 4, 5}, new int[]{6, 7, 8, 9, 10, 11}, new int[0]),
                arguments(new int[]{1, 2, 2}, new int[]{2, 2}, new int[]{2}),
                arguments(new int[]{3, 5, 7}, new int[]{3, 7, 8, 9}, new int[]{3, 7})
        );
    }

}