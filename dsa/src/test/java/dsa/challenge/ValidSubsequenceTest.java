package dsa.challenge;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ValidSubsequenceTest {

    @ParameterizedTest(name = "{0}: arr={1}, sequence={2}, out={3}")
    @MethodSource("happyPath")
    void testHappyPath(ValidSubsequence validSubsequence, int[] arr, int[] sequence, boolean out) {
        assertEquals(out, validSubsequence.validate(arr, sequence));
    }

    @MethodSource
    static Stream<Arguments> happyPath() {
        return Stream.of(
                arguments(new ValidSubsequence.WhileLoop(), new int[]{5, 1, 22, 25, 6, -1, 8, 10}, new int[]{1, 6, -1, 10}, true),
                arguments(new ValidSubsequence.WhileLoop(), new int[]{1, 2, 3}, new int[]{1, 2, 3}, true),
                arguments(new ValidSubsequence.WhileLoop(), new int[]{1, 2, 3}, new int[]{2}, true),
                arguments(new ValidSubsequence.WhileLoop(), new int[]{1, 2, 3}, new int[]{4}, false),
                arguments(new ValidSubsequence.WhileLoop(), new int[]{1, 2, 3}, new int[]{3, 2}, false),
                arguments(new ValidSubsequence.WhileLoop(), new int[]{1, 2}, new int[]{1, 2, 3}, false),
                arguments(new ValidSubsequence.WhileLoop(), new int[]{}, new int[]{}, true),
                arguments(new ValidSubsequence.WhileLoop(), new int[]{1, 2, 3}, new int[]{}, true),
                arguments(new ValidSubsequence.WhileLoop(), new int[]{}, new int[]{1}, false),
                arguments(new ValidSubsequence.ForLoop(), new int[]{5, 1, 22, 25, 6, -1, 8, 10}, new int[]{1, 6, -1, 10}, true),
                arguments(new ValidSubsequence.ForLoop(), new int[]{1, 2, 3}, new int[]{1, 2, 3}, true),
                arguments(new ValidSubsequence.ForLoop(), new int[]{1, 2, 3}, new int[]{2}, true),
                arguments(new ValidSubsequence.ForLoop(), new int[]{1, 2, 3}, new int[]{4}, false),
                arguments(new ValidSubsequence.ForLoop(), new int[]{1, 2, 3}, new int[]{3, 2}, false),
                arguments(new ValidSubsequence.ForLoop(), new int[]{1, 2}, new int[]{1, 2, 3}, false),
                arguments(new ValidSubsequence.ForLoop(), new int[]{}, new int[]{}, true),
                arguments(new ValidSubsequence.ForLoop(), new int[]{1, 2, 3}, new int[]{}, true),
                arguments(new ValidSubsequence.ForLoop(), new int[]{}, new int[]{1}, false)
        );
    }

}