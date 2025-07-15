package dsa.array;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static dsa.array.Arrays.isSorted;
import static dsa.array.Arrays.swap;
import static org.junit.jupiter.api.Assertions.*;

class ArraysTest {

    @Nested
    class Swap {
        @Test
        void testSwapTwoDifferentElements() {
            int[] arr = {1, 2, 3, 4};
            swap(arr, 1, 3);
            assertArrayEquals(new int[]{1, 4, 3, 2}, arr);
        }
        @Test
        void testSwapSameIndex() {
            int[] arr = {5, 6, 7};
            swap(arr, 2, 2);
            assertArrayEquals(new int[]{5, 6, 7}, arr);
        }
        @Test
        void testSwapFirstAndLast() {
            int[] arr = {9, 8, 7};
            swap(arr, 0, 2);
            assertArrayEquals(new int[]{7, 8, 9}, arr);
        }
        @Test
        void testSwapNegativeIndexThrowsException() {
            int[] arr = {1, 2, 3};
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> swap(arr, -1, 2));
        }
        @Test
        void testSwapIndexOutOfBoundsThrowsException() {
            int[] arr = {10, 20, 30};
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> swap(arr, 0, 5));
        }
    }

    @Nested
    class IsSorted {
        @Test
         void testEmptyArray() {
            assertTrue(isSorted(new int[] {}));
        }
        @Test
        void testSingleElement() {
            assertTrue(isSorted(new int[]{42}));
        }
        @Test
        void testSortedAscending() {
            assertTrue(isSorted(new int[]{1, 2, 3, 4, 5}));
        }
        @Test
        void testSortedWithDuplicates() {
            assertTrue(isSorted(new int[]{1, 2, 2, 3, 4}));
        }
        @Test
        void testUnsorted() {
            assertFalse(isSorted(new int[]{5, 4, 3, 2, 1}));
        }
        @Test
        void testUnsortedMiddleElement() {
            assertFalse(isSorted(new int[]{1, 2, 4, 3, 5}));
        }
    }

}