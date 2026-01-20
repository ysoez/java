package dsa.challenge;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;
import dsa.array.Arrays;

import java.util.HashMap;
import java.util.Set;

import static dsa.Algorithm.Assumption.ORDERING;
import static dsa.Algorithm.Complexity.*;

interface TwoSum {

    Set<TwoSum> IMPLEMENTATIONS = Set.of(BruteForce.INSTANCE, TwoPointers.INSTANCE, HashMapOptimized.INSTANCE);

    int[] getIndices(int[] numbers, int sum);

    class BruteForce implements TwoSum {
        static final TwoSum INSTANCE = new BruteForce();
        @Override
        @Algorithm(complexity = @Complexity(runtime = QUADRATIC, space = CONSTANT))
        public int[] getIndices(int[] numbers, int sum) {
            for (int i = 0; i < numbers.length; i++) {
                int ntf = sum - numbers[i];
                for (int j = i + 1; j < numbers.length; j++)
                    if (numbers[j] == ntf)
                        return new int[]{i, j};
            }
            return Arrays.EMPTY_INT_ARR;
        }
        @Override
        public String toString() {
            return getClass().getSimpleName();
        }
    }

    class TwoPointers implements TwoSum {
        static final TwoSum INSTANCE = new TwoPointers();
        @Override
        @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT), assumptions = ORDERING)
        public int[] getIndices(int[] numbers, int sum) {
            int left = 0;
            int right = numbers.length - 1;
            while (left < right) {
                var currentSum = numbers[left] + numbers[right];
                if (currentSum == sum)
                    return new int[]{left, right};
                if (currentSum < sum)
                    left++;
                else
                    right--;
            }
            return Arrays.EMPTY_INT_ARR;
        }
        @Override
        public String toString() {
            return getClass().getSimpleName();
        }
    }

    class HashMapOptimized implements TwoSum {
        static final TwoSum INSTANCE = new HashMapOptimized();
        @Override
        @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
        public int[] getIndices(int[] numbers, int sum) {
            var numberToIndex = new HashMap<Integer, Integer>();
            for (int i = 0; i < numbers.length; i++) {
                var val = sum - numbers[i];
                if (numberToIndex.containsKey(val))
                    return new int[]{numberToIndex.get(val), i};
                numberToIndex.put(numbers[i], i);
            }
            return Arrays.EMPTY_INT_ARR;
        }
        @Override
        public String toString() {
            return getClass().getSimpleName();
        }
    }
}
