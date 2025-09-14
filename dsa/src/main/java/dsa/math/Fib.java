package dsa.math;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.ArrayDeque;

import static dsa.Algorithm.Complexity.EXPONENTIAL;
import static dsa.Algorithm.Complexity.LINEAR;

interface Fib {

    int get(int val);

    class RecursiveFib implements Fib {
        @Override
        @Algorithm(complexity = @Complexity(runtime = EXPONENTIAL, space = LINEAR))
        public int get(int val) {
            if (val < 0)
                throw new IllegalArgumentException();
            if (val == 0)
                return 0;
            if (val == 1)
                return 1;
            return get(val - 1) + get(val - 2);
        }
    }

    class IterativeFib implements Fib {
        @Override
        @Algorithm(complexity = @Complexity(runtime = EXPONENTIAL, space = LINEAR))
        public int get(int val) {
            if (val < 0)
                throw new IllegalArgumentException();
            var stack = new ArrayDeque<Integer>();
            stack.push(val);
            var sum = 0;
            while (!stack.isEmpty()) {
                int n = stack.pop();
                if (n == 0)
                    sum += 0;
                else if (n == 1) {
                    sum += 1;
                } else {
                    stack.push(n - 1);
                    stack.push(n - 2);
                }
            }
            return sum;
        }
    }

}
