package dsa.sort;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SortTest {

    @ParameterizedTest
    @MethodSource("sortAlgorithms")
    void testAsc(Sort sort) {
        int[] numbers = {5, 3, 10, 1, 4, 2};
        var result = sort.asc(numbers);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 10}, result);
    }

    @ParameterizedTest
    @MethodSource("sortAlgorithms")
    void testDesc(Sort sort) {
        int[] numbers = {5, 3, 10, 1, 4, 2};
        var result = sort.desc(numbers);
        assertArrayEquals(new int[]{10, 5, 4, 3, 2, 1}, result);
    }

    static Stream<Sort> sortAlgorithms() {
        return Stream.of(new HeapSort());
    }

}