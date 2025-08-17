package dsa.challenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransposeMatrixTest {

    private final TransposeMatrix transcoder = new TransposeMatrix();

    @Test
    void testSquareMatrixTranspose() {
        int[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int[][] expected = {
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };
        assertArrayEquals(expected, transcoder.apply(input));
    }

    @Test
    void testRectangularMatrixTranspose() {
        int[][] input = {
                {1, 2},
                {3, 4},
                {5, 6}
        };
        int[][] expected = {
                {1, 3, 5},
                {2, 4, 6}
        };
        assertArrayEquals(expected, transcoder.apply(input));
    }

    @Test
    void testSingleElementMatrix() {
        int[][] input = {{42}};
        int[][] expected = {{42}};
        assertArrayEquals(expected, transcoder.apply(input));
    }

    @Test
    void testOneRowMatrix() {
        int[][] input = {{1, 2, 3, 4}};
        int[][] expected = {
                {1},
                {2},
                {3},
                {4}
        };
        assertArrayEquals(expected, transcoder.apply(input));
    }

    @Test
    void testOneColumnMatrix() {
        int[][] input = {
                {1},
                {2},
                {3},
                {4}
        };
        int[][] expected = {{1, 2, 3, 4}};
        assertArrayEquals(expected, transcoder.apply(input));
    }

    @Test
    void testEmptyMatrix() {
        int[][] input = new int[0][0];
        int[][] expected = new int[0][0];
        assertArrayEquals(expected, transcoder.apply(input));
    }

}