package dsa.challenge;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Assumption.ORDERING;
import static dsa.Algorithm.Complexity.LINEAR;
import static dsa.Algorithm.Complexity.LINEARITHMIC;
import static java.util.Arrays.sort;

public interface SquareSortedArray {

    int[] apply(int[] arr);

    class BruteForce implements SquareSortedArray {
        @Override
        @Algorithm(complexity = @Complexity(runtime = LINEARITHMIC, space = LINEAR))
        public int[] apply(int[] arr) {
            var squared = new int[arr.length];
            for (int i = 0; i < arr.length; i++)
                squared[i] = (arr[i] * arr[i]);
            sort(squared);
            return squared;
        }
        @Override
        public String toString() {
            return getClass().getSimpleName();
        }
    }

    class MultiPointers implements SquareSortedArray {
        @Override
        @Algorithm(assumptions = {ORDERING}, complexity = @Complexity(runtime = LINEAR, space = LINEAR))
        public int[] apply(int[] arr) {
            var squared = new int[arr.length];
            var startIdx = 0;
            var endIdx = arr.length - 1;
            var i = arr.length - 1;
            while (startIdx <= endIdx) {
                var left = Math.abs(arr[startIdx]);
                var right = Math.abs(arr[endIdx]);
                //
                // ~ move one pointer at a time to populate duplicates
                //
                if (left > right) {
                    squared[i] = left * left;
                    startIdx++;
                } else {
                    squared[i] = right * right;
                    endIdx--;
                }
                i--;
            }
            return squared;
        }
        @Override
        public String toString() {
            return getClass().getSimpleName();
        }
    }

}
