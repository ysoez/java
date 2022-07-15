package leetcode;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static leetcode._1_TwoSum.find;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class _1_TwoSumTest {

    @ParameterizedTest
    @MethodSource("dataset")
    void checkResult(int[] nums, int target, int[] result) {
        assertArrayEquals(result, find(nums, target));
    }

    static Stream<Arguments> dataset() {
        return Stream.of(
                arguments(new int[]{2, 7, 11, 15}, 9, new int[]{0, 1}),
                arguments(new int[]{3, 2, 4}, 6, new int[]{1, 2}),
                arguments(new int[]{3, 3}, 6, new int[]{0, 1})
        );
    }

}