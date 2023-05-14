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

}