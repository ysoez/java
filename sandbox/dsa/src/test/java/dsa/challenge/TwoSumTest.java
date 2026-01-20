package dsa.challenge;

import dsa.array.Arrays;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TwoSumTest {

    @ParameterizedTest(name = "numbers: {0}, sum: {1}, expected: {2}")
    @MethodSource("happyPath")
    void testHappyPath(int[] numbers, int sum, int[] expected) {
        for (TwoSum twoSum : TwoSum.IMPLEMENTATIONS) {
            assertArrayEquals(expected, twoSum.getIndices(numbers, sum));
        }
    }

    @ParameterizedTest(name = "numbers: {0}, sum: {1}, expected: {2}")
    @MethodSource("unsortedNumbers")
    void testUnorderedNumbers(int[] numbers, int sum, int[] expected) {
        for (TwoSum twoSum : Set.of(TwoSum.BruteForce.INSTANCE)) {
            assertArrayEquals(expected, twoSum.getIndices(numbers, sum));
        }
    }

    @ParameterizedTest(name = "numbers: {0}, sum: {1}, expected: {2}")
    @MethodSource("cornerCases")
    void testCornerCases(int[] numbers, int sum, int[] expected) {
        for (TwoSum twoSum : TwoSum.IMPLEMENTATIONS) {
            assertArrayEquals(expected, twoSum.getIndices(numbers, sum));
        }
    }

    @MethodSource
    static Stream<Arguments> happyPath() {
        return Stream.of(
                arguments(new int[]{2, 7, 11, 15}, 9, new int[]{0, 1}),
                arguments(new int[]{3, 3}, 6, new int[]{0, 1})
        );
    }

    @MethodSource
    static Stream<Arguments> unsortedNumbers() {
        return Stream.of(
                arguments(new int[]{3, 2, 4}, 6, new int[]{1, 2}),
                arguments(new int[]{1, 3, 7, 9, 2}, 11, new int[]{3, 4}),
                arguments(new int[]{-3, 4, -1, 8}, -4, new int[]{0, 2}),
                arguments(new int[]{1, 4, 3, 4}, 8, new int[]{1, 3}),
                arguments(new int[]{Integer.MAX_VALUE - 1, 1, 2}, Integer.MAX_VALUE, new int[]{0, 1}),
                arguments(new int[]{-1, 0, 1, 0}, 0, new int[]{0, 2})
        );
    }

    @MethodSource
    static Stream<Arguments> cornerCases() {
        return Stream.of(
                arguments(new int[]{1, 2, 3, 4}, 10, Arrays.EMPTY_INT_ARR),
                arguments(Arrays.EMPTY_INT_ARR, 0, Arrays.EMPTY_INT_ARR)
        );
    }

}