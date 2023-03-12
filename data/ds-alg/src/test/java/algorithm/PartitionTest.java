package algorithm;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PartitionTest {

    @ParameterizedTest
    @MethodSource("dataset")
    void partitioning(List<Integer> source, int batchSize, List<List<Integer>> expectedResult) {
        assertEquals(expectedResult, Partition.forList(source, batchSize));
    }

    static Stream<Arguments> dataset() {
        return Stream.of(
                arguments(List.of(1, 2, 3, 4, 5), 3, List.of(List.of(1, 2, 3), List.of(4, 5))),
                arguments(List.of(1, 2, 3, 4, 5, 6, 7, 8), 4, List.of(List.of(1, 2, 3, 4), List.of(5, 6, 7, 8))),
                arguments(List.of(1, 2, 3, 4, 5, 6, 7, 8), 3, List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8)))
        );
    }

}
