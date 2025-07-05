package dsa.array;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ArraysTest {

    @Nested
    class TwoSum {

        @ParameterizedTest
        @MethodSource("implementations")
        void testHappyPath(Arrays.TwoSum twoSum) {
            int[] arr = {2, 7, 11, 15};
            int target = 9;
            int[] expected = {0, 1};
            assertArrayEquals(expected, twoSum.getIndices(arr, target));

            arr = new int[]{1, 3, 7, 9, 2};
            target = 11;
            expected = new int[]{3, 4};
            assertArrayEquals(expected, twoSum.getIndices(arr, target));
        }

//        @Test
//        void testTwoSumNegativeNumbers() {
//            int[] arr = {-3, 4, -1, 8};
//            int target = -4;
//            int[] expected = {0, 2};
//            assertArrayEquals(expected, Arrays.twoSum(arr, target));
//        }
//
//        @Test
//        void testTwoSumTargetNotFound() {
//            int[] arr = {1, 2, 3, 4};
//            int target = 10;
//            int[] expected = {};
//            assertArrayEquals(expected, Arrays.twoSum(arr, target));
//        }
//
//        @Test
//        void testTwoSumDuplicateNumbers() {
//            int[] arr = {3, 3, 4, 5};
//            int target = 6;
//            int[] expected = {0, 1};
//            assertArrayEquals(expected, Arrays.twoSum(arr, target));
//        }
//
//        @Test
//        void testTwoSumMultiplePairs() {
//            int[] arr = {1, 4, 3, 4};
//            int target = 8;
//            int[] expected = {1, 3}; // Assuming function returns first valid pair
//            assertArrayEquals(expected, Arrays.twoSum(arr, target));
//        }
//
//        @Test
//        void testTwoSumSingleElementArray() {
//            int[] arr = {1};
//            int target = 2;
//            //npe
////            Arrays.twoSum(arr, target);
//        }
//
//        @Test
//        void testTwoSumTwoElementArray() {
//            int[] arr = {1, 6};
//            int target = 7;
//            int[] expected = {0, 1};
//            assertArrayEquals(expected, Arrays.twoSum(arr, target));
//        }
//
//        @Test
//        public void testTwoSumNullArray() {
//            int[] arr = null;
//            int target = 5;
//            // npe
////            TwoSum.twoSum(arr, target);
//        }
//
//        @Test
//        public void testTwoSumEmptyArray() {
//            int[] arr = {};
//            int target = 0;
//            int[] expected = {};
//            assertArrayEquals(expected, Arrays.twoSum(arr, target));
//        }
//
//        @Test
//        public void testTwoSumLargeNumbers() {
//            int[] arr = {Integer.MAX_VALUE - 1, 1, 2};
//            int target = Integer.MAX_VALUE;
//            int[] expected = {0, 1};
//            assertArrayEquals(expected, Arrays.twoSum(arr, target));
//        }
//
//        @Test
//        public void testTwoSumZeroTarget() {
//            int[] arr = {-1, 0, 1, 0};
//            int target = 0;
//            int[] expected = {0, 2};
//            assertArrayEquals(expected, Arrays.twoSum(arr, target));
//        }

        @MethodSource
        static Stream<Arguments> implementations() {
            return Stream.of(
                    arguments(new Arrays.TwoSum.BruteForce()),
                    arguments(new Arrays.TwoSum.HashMapOptimized())
            );
        }
    }

    @Nested
    class MaxWaterContainer {

        @ParameterizedTest
        @MethodSource("implementations")
        void testHappyPath(Arrays.MaxWaterContainer maxWaterContainer) {
            int[] input = new int[]{7, 1, 2, 3, 9};
            //
            // ~ length = 7, width = 4, area = 4 * 7 = 28
            //
            assertEquals(28, maxWaterContainer.getArea(input));
            //
            // ~ length = ?, width = ?, area = ?
            //
            input = new int[]{4, 8, 1, 2, 3, 9};
            assertEquals(32, maxWaterContainer.getArea(input));
            //
            // ~ length = 4, width = 2, area = ?
            //
            //  => 8, 4 (bigger area)
            input = new int[]{6, 9, 3, 4, 5, 8};
            assertEquals(32, maxWaterContainer.getArea(input));
        }

        @Test
        void testEmptyArray() {
            assertEquals(0, Arrays.MaxWaterContainer.BRUTE_FORCE.getArea(new int[0]));
        }

        @Test
        void testSingleElementArray() {
            assertEquals(0, Arrays.MaxWaterContainer.BRUTE_FORCE.getArea(new int[]{7}));
        }

        @MethodSource
        static Stream<Arguments> implementations() {
            return Stream.of(
                    arguments(Arrays.MaxWaterContainer.BRUTE_FORCE),
                    arguments(Arrays.MaxWaterContainer.SHIFTING_POINTERS)
            );
        }
    }

//    @Nested
//    class MergeSorted {
//
//        @Test
//        void testTwoNonEmptyArrays() {
//            int[] arr1 = {1, 3, 5};
//            int[] arr2 = {2, 4, 6};
//            int[] expected = {1, 2, 3, 4, 5, 6};
//            assertArrayEquals(expected, Arrays.MergeSorted.BASIC.getMerged(arr1, arr2));
//        }
//
//        @Test
//        void testEmptyFirstArray() {
//            int[] arr1 = {};
//            int[] arr2 = {1, 2, 3};
//            int[] expected = {1, 2, 3};
//            assertArrayEquals(expected, Arrays.MergeSorted.BASIC.getMerged(arr1, arr2));
//        }
//
//        @Test
//        void testEmptySecondArray() {
//            int[] arr1 = {1, 2, 3};
//            int[] arr2 = {};
//            int[] expected = {1, 2, 3};
//            assertArrayEquals(expected, Arrays.MergeSorted.BASIC.getMerged(arr1, arr2));
//        }
//
//        @Test
//        void testBothEmptyArrays() {
//            int[] arr1 = {};
//            int[] arr2 = {};
//            int[] expected = {};
//            assertArrayEquals(expected, Arrays.MergeSorted.BASIC.getMerged(arr1, arr2));
//        }
//
//        @Test
//        void testArraysWithDuplicates() {
//            int[] arr1 = {1, 2, 2};
//            int[] arr2 = {2, 3, 4};
//            int[] expected = {1, 2, 2, 2, 3, 4};
//            assertArrayEquals(expected, Arrays.MergeSorted.BASIC.getMerged(arr1, arr2));
//        }
//
//        @Test
//        void testArraysDifferentSizes() {
//            int[] arr1 = {1, 2};
//            int[] arr2 = {3, 4, 5, 6};
//            int[] expected = {1, 2, 3, 4, 5, 6};
//            assertArrayEquals(expected, Arrays.MergeSorted.BASIC.getMerged(arr1, arr2));
//        }
//
//        @Test
//        void testArraysWithNegativeNumbers() {
//            int[] arr1 = {-3, -1, 0};
//            int[] arr2 = {-2, 1, 2};
//            int[] expected = {-3, -2, -1, 0, 1, 2};
//            assertArrayEquals(expected, Arrays.MergeSorted.BASIC.getMerged(arr1, arr2));
//        }
//
//        @Test
//        void testSingleElementArrays() {
//            int[] arr1 = {1};
//            int[] arr2 = {2};
//            int[] expected = {1, 2};
//            assertArrayEquals(expected, Arrays.MergeSorted.BASIC.getMerged(arr1, arr2));
//        }
//
//        @Test
//        void testNullFirstArray() {
//            int[] arr1 = null;
//            int[] arr2 = {1, 2, 3};
//            assertArrayEquals(arr2, Arrays.MergeSorted.BASIC.getMerged(arr1, arr2));
//        }
//
//        @Test
//        void testNullSecondArray() {
//            int[] arr1 = {1, 2, 3};
//            int[] arr2 = null;
//            assertArrayEquals(arr1, Arrays.MergeSorted.BASIC.getMerged(arr1, arr2));
//        }
//
//    }

}