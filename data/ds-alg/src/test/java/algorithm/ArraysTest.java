package algorithm;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ArraysTest {

    @ParameterizedTest
    @MethodSource("mostFrequentDataset")
    void mostFrequent(int[] input, Integer expectedResult) {
        assertEquals(expectedResult, Arrays.mostFrequent(input));
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

}