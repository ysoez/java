package dsa.challenge;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SquareSortedArrayTest {

    @ParameterizedTest(name = "{0}: arr={1}, expected={2}")
    @MethodSource("happyPath")
    void testHappyPath(SquareSortedArray squareSortedArray, int[] arr, int[] expected) {
        assertArrayEquals(expected, squareSortedArray.apply(arr));
    }

    @MethodSource
    static Stream<Arguments> happyPath() {
        return Stream.of(
                arguments(new SquareSortedArray.BruteForce(), new int[]{-4, -1, 0, 3, 10}, new int[]{0, 1, 9, 16, 100}),
                arguments(new SquareSortedArray.BruteForce(), new int[]{-7, -3, 2, 3, 11}, new int[]{4, 9, 9, 49, 121}),
                arguments(new SquareSortedArray.MultiPointers(), new int[]{-4, -1, 0, 3, 10}, new int[]{0, 1, 9, 16, 100}),
                arguments(new SquareSortedArray.MultiPointers(), new int[]{-7, -3, 2, 3, 11}, new int[]{4, 9, 9, 49, 121})
        );
    }
}