package algorithm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ArraysTest {

    @ParameterizedTest
    @MethodSource("mostFrequentDataset")
    void mostFrequent(int[] input, Integer expectedResult) {
        assertEquals(expectedResult, Arrays.mostFrequent(input));
    }

    @ParameterizedTest
    @MethodSource("isRotationDataset")
    void isRotation(int[] first, int[] second, boolean expectedResult) {
        assertEquals(expectedResult, Arrays.isRotation(first, second));
    }

    @ParameterizedTest
    @MethodSource("sortedArrayIntersectionDataSet")
    void sortedArrayIntersection(int[] first, int[] second, int[] expected) {
        assertArrayEquals(expected, Arrays.intersectionForSorted(first, second));
    }

    @Test
    void rotate2D() {
        assertArrayEquals(
                new int[][]{{7, 4, 1}, {8, 5, 2}, {9, 6, 3}},
                Arrays.rotate2D(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, 3)
        );
        assertArrayEquals(
                new int[][]{{7, 4, 1}, {8, 5, 2}, {9, 6, 3}},
                Arrays.rotate2DInPlace(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, 3)
        );
        assertArrayEquals(
                new int[][]{{13, 9, 5, 1}, {14, 10, 6, 2}, {15, 11, 7, 3}, {16, 12, 8, 4}},
                Arrays.rotate2D(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}}, 4)
        );
        assertArrayEquals(
                new int[][]{{13, 9, 5, 1}, {14, 10, 6, 2}, {15, 11, 7, 3}, {16, 12, 8, 4}},
                Arrays.rotate2DInPlace(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}}, 4)
        );
    }

    @Test
    void mineSweeper() {
        assertArrayEquals(
                new int[][]{{0, 1, -1}, {1, 2, 1}, {-1, 1, 0}},
                Arrays.mineSweeper(new int[][]{{0, 2}, {2, 0}}, 3, 3)
        );
        assertArrayEquals(
                new int[][]{{-1, -1, 2, 1}, {2, 3, -1, 1}, {0, 1, 1, 1}},
                Arrays.mineSweeper(new int[][]{{0, 0}, {0, 1}, {1, 2}}, 3, 4)
        );
        assertArrayEquals(
                new int[][]{{1, 2, 2, 1, 0}, {1, -1, -1, 2, 0}, {1, 3, -1, 2, 0}, {0, 1, 2, 2, 1}, {0, 0, 1, -1, 1}},
                Arrays.mineSweeper(new int[][]{{1, 1}, {1, 2}, {2, 2}, {4, 3}}, 5, 5)
        );
    }

    static Stream<Arguments> mostFrequentDataset() {
        return Stream.of(
                arguments(new int[]{1, 3, 1, 3, 2, 1}, 1),
                arguments(new int[]{3, 3, 1, 3, 2, 1}, 3),
                arguments(new int[]{}, null),
                arguments(new int[]{0}, 0),
                arguments(new int[]{0, -1, 10, 10, -1, 10, -1, -1, -1, 1}, -1)
        );
    }

    static Stream<Arguments> isRotationDataset() {
        return Stream.of(
                arguments(null, null, false),
                arguments(new int[0], null, false),
                arguments(null, new int[0], false),
                arguments(new int[0], new int[1], false),
                arguments(new int[]{1, 2, 3, 4, 5, 6, 7}, new int[]{4, 5, 6, 7, 8, 1, 2, 3}, false),
                arguments(new int[]{1, 2, 3, 4, 5, 6, 7}, new int[]{4, 5, 6, 7, 1, 2, 3}, true),
                arguments(new int[]{1, 2, 3, 4, 5, 6, 7}, new int[]{4, 5, 6, 9, 1, 2, 3}, false),
                arguments(new int[]{1, 2, 3, 4, 5, 6, 7}, new int[]{4, 6, 5, 7, 1, 2, 3}, false),
                arguments(new int[]{1, 2, 3, 4, 5, 6, 7}, new int[]{4, 5, 6, 7, 0, 2, 3}, false),
                arguments(new int[]{1, 2, 3, 4, 5, 6, 7}, new int[]{1, 2, 3, 4, 5, 6, 7}, true)
        );
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