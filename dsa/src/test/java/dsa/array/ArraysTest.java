package dsa.array;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static dsa.array.Arrays.isSorted;
import static dsa.array.Arrays.swap;
import static org.junit.jupiter.api.Assertions.*;

class ArraysTest {

    @Nested
    class NewArray {
        @Test
        void testNewArrayWithPositiveCapacity() {
            Object[] arr = Arrays.newArray(5);
            assertNotNull(arr);
            assertEquals(5, arr.length);
        }
        @Test
        void testNewArrayWithZeroCapacity() {
            Object[] arr = Arrays.newArray(0);
            assertNotNull(arr);
            assertEquals(0, arr.length);
        }
        @Test
        void testNewArrayWithNegativeCapacity() {
            assertThrows(NegativeArraySizeException.class, () -> Arrays.newArray(-1));
        }
    }

    @Nested
    class CheckCapacity {
        @Test
        void testCheckCapacityWithValidInput() {
            assertDoesNotThrow(() -> Arrays.checkCapacity(10));
        }
        @Test
        void testCheckCapacityWithZero() {
            assertDoesNotThrow(() -> Arrays.checkCapacity(0));
        }
        @Test
        void testCheckCapacityWithNegativeInput() {
            assertThrows(NegativeArraySizeException.class, () -> Arrays.checkCapacity(-5));
        }
    }

    @Nested
    class CheckIndex {
        @Test
        void testCheckIndexValid() {
            assertDoesNotThrow(() -> Arrays.checkIndex(2, 5));
        }
        @Test
        void testCheckIndexEqualToSize() {
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.checkIndex(5, 5));
        }
        @Test
        void testCheckIndexGreaterThanSize() {
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.checkIndex(6, 5));
        }
        @Test
        void testCheckIndexNegative() {
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.checkIndex(-1, 5));
        }
        @Test
        void testCheckIndexWithEmptyArray() {
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.checkIndex(0, 0));
        }
    }

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
            assertTrue(isSorted(new int[]{}));
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

    @Nested
    class RemoveDuplicates {
        @Test
        void testNullArray() {
            assertEquals(0, Arrays.removeDuplicates(null));
        }
        @Test
        void testEmptyArray() {
            int[] nums = {};
            int uniqueCount = Arrays.removeDuplicates(new int[]{});
            assertEquals(0, uniqueCount);
            assertArrayEquals(new int[]{}, nums);
        }
        @Test
        void testSingleElement() {
            int[] nums = {1};
            int uniqueCount = Arrays.removeDuplicates(nums);
            assertEquals(1, uniqueCount);
            assertArrayEquals(new int[]{1}, nums);
        }
        @Test
        void testNoDuplicates() {
            int[] nums = {1, 2, 3, 4};
            int uniqueCount = Arrays.removeDuplicates(nums);
            assertEquals(4, uniqueCount);
            assertArrayEquals(new int[]{1, 2, 3, 4}, nums);
        }
        @Test
        void testAllDuplicates() {
            int[] nums = {1, 1, 1, 1};
            int uniqueCount = Arrays.removeDuplicates(nums);
            assertEquals(1, uniqueCount);
            int[] expected = new int[nums.length];

            expected[0] = 1;
            for (int i = 0; i < uniqueCount; i++) {
                assertEquals(expected[i], nums[i]);
            }
        }
        @Test
        void testSomeDuplicates() {
            int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
            int uniqueCount = Arrays.removeDuplicates(nums);
            assertEquals(5, uniqueCount);
            int[] expected = new int[]{0, 1, 2, 3, 4};
            for (int i = 0; i < uniqueCount; i++) {
                assertEquals(expected[i], nums[i]);
            }
        }
        @Test
        void testNegativeNumbers() {
            int[] nums = {-3, -3, -2, -1, -1, 0};
            int uniqueCount = Arrays.removeDuplicates(nums);
            assertEquals(4, uniqueCount);
            int[] expected = new int[]{-3, -2, -1, 0};
            for (int i = 0; i < uniqueCount; i++) {
                assertEquals(expected[i], nums[i]);
            }
        }
    }

}