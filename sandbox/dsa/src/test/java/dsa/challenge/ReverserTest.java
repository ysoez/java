package dsa.challenge;

import dsa.array.Arrays;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ReverserTest {

    @Nested
    class ArrayReverser {
        @Test
        void testEmptyArray() {
            var arr = Arrays.EMPTY_INT_ARR;
            Reverser.ArrayReverser.INSTANCE.reverse(arr);
            assertArrayEquals(Arrays.EMPTY_INT_ARR, arr);
        }
        @Test
        void testSingleElementArray() {
            var arr = new int[]{10};
            Reverser.ArrayReverser.INSTANCE.reverse(arr);
            assertArrayEquals(new int[]{10}, arr);
        }
        @Test
        void testTwoSingleElementArray() {
            var arr = new int[]{5, 10};
            Reverser.ArrayReverser.INSTANCE.reverse(arr);
            assertArrayEquals(new int[]{10, 5}, arr);
        }
        @Test
        void testThreeSingleElementArray() {
            var arr = new int[]{20, 10, 5};
            Reverser.ArrayReverser.INSTANCE.reverse(arr);
            assertArrayEquals(new int[]{5, 10, 20}, arr);
        }
    }

}