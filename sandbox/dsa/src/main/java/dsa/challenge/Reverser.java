package dsa.challenge;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Complexity.*;

interface Reverser<T> {

    void reverse(T t);

    class ArrayReverser implements Reverser<int[]> {
        static final ArrayReverser INSTANCE = new ArrayReverser();
        @Override
        @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
        public void reverse(int[] ints) {
            if (ints == null || ints.length < 2)
                return;
            var left = 0;
            var right = ints.length - 1;
            while (left < right) {
                var rightVal = ints[right];
                ints[right--] = ints[left];
                ints[left++] = rightVal;
            }
        }
    }

}
