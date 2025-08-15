package dsa.challenge;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Complexity.Value.CONSTANT;
import static dsa.Algorithm.Complexity.Value.LINEAR;

interface ValidSubsequence {

    boolean validate(int[] arr, int[] sequence);

    class WhileLoop implements ValidSubsequence {
        @Override
        @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
        public boolean validate(int[] arr, int[] sequence) {
            var arrIdx = 0;
            var seqIdx = 0;
            while (arrIdx < arr.length && seqIdx < sequence.length) {
                if (arr[arrIdx] == sequence[seqIdx])
                    seqIdx++;
                arrIdx++;
            }
            return seqIdx == sequence.length;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName();
        }
    }

    class ForLoop implements ValidSubsequence {
        @Override
        @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
        public boolean validate(int[] arr, int[] sequence) {
            var seqIdx = 0;
            for (int num : arr) {
                if (seqIdx == sequence.length)
                    break;
                if (num == sequence[seqIdx])
                    seqIdx++;
            }
            return seqIdx == sequence.length;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName();
        }
    }
}
