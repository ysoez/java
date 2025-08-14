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