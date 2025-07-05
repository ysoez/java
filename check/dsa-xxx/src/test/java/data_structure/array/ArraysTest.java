package data_structure.array;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ArraysTest {

    @Test
    void max() {
        assertThrows(IllegalArgumentException.class, () -> Arrays.max(null));
        assertThrows(EmptyArrayException.class, () -> Arrays.max(new int[0]));

        assertEquals(1, Arrays.max(new int[]{1}));

        assertEquals(2, Arrays.max(new int[]{1, 2}));
        assertEquals(2, Arrays.max(new int[]{2, 1}));

        assertEquals(78, Arrays.max(new int[]{10, 21, 16, 78, 62, 5}));
        assertEquals(30, Arrays.max(new int[]{10, 20, 30}));
    }

    @ParameterizedTest
    @MethodSource("sortedArrayIntersectionDataSet")
    void sortedArrayIntersection(int[] first, int[] second, int[] expected) {
        assertArrayEquals(expected, Arrays.intersectionForSorted(first, second));
    }

    private static Stream<Arguments> sortedArrayIntersectionDataSet() {
        return Stream.of(
                arguments(new int[]{1, 3, 4, 6, 7, 9}, new int[]{1, 2, 4, 5, 9, 10}, new int[]{1, 4, 9}),
                arguments(new int[]{1, 2, 9, 10, 11, 12}, new int[]{0, 1, 2, 3, 4, 5, 8, 9, 10, 12, 14, 15}, new int[]{1, 2, 9, 10, 12}),
                arguments(new int[]{0, 1, 2, 3, 4, 5}, new int[]{6, 7, 8, 9, 10, 11}, new int[0]),
                arguments(new int[]{1, 2, 2}, new int[]{2, 2}, new int[]{2}),
                arguments(new int[]{3, 5, 7}, new int[]{3, 7, 8, 9}, new int[]{3, 7})
        );
    }

    @Test
    void reverseIntArray() {
        assertThrows(IllegalArgumentException.class, () -> Arrays.reverse(null));
        assertArrayEquals(new int[0], Arrays.reverse(new int[0]));
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, Arrays.reverse(new int[]{1, 2, 3, 4, 5}));
        assertArrayEquals(new int[]{40, 30, 20, 10}, Arrays.reverse(new int[]{10, 20, 30, 40}));
    }

    @ParameterizedTest
    @MethodSource("twoSumDataSet")
    void twoSum(int[] nums, int target, int[] result) {
        assertArrayEquals(result, Arrays.twoSum(nums, target));
    }

    private static Stream<Arguments> twoSumDataSet() {
        return Stream.of(
                arguments(new int[]{2, 7, 11, 15}, 9, new int[]{0, 1}),
                arguments(new int[]{3, 2, 4}, 6, new int[]{1, 2}),
                arguments(new int[]{3, 3}, 6, new int[]{0, 1})
        );
    }




}