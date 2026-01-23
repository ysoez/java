package dsa.challenge;

import dsa.Algorithm;
import dsa.array.Arrays;

import java.util.LinkedHashSet;
import java.util.function.UnaryOperator;

import static dsa.Algorithm.Complexity.LINEAR;

interface Deduplicator<T> extends UnaryOperator<T> {

    class ArrayDeduplicator implements Deduplicator<int[]> {
        static final ArrayDeduplicator INSTANCE = new ArrayDeduplicator();
        @Override
        @Algorithm(complexity = @Algorithm.Complexity(runtime = LINEAR, space = LINEAR))
        public int[] apply(int[] ints) {
            if (Arrays.isEmpty(ints))
                return ints;
            var set = new LinkedHashSet<Integer>();
            for (int num : ints)
                set.add(num);
            var arr = new int[set.size()];
            int i = 0;
            for (Integer num : set)
                arr[i++] = num;
            return arr;
        }
    }

}
