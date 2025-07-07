package dsa.math;

interface Factorial {

    int get(int value);

    class LoopFactorial implements Factorial {
        @Override
        public int get(int n) {
            var factorial = 1;
            for (int i = n; i > 1; i--)
                factorial *= i;
            return factorial;
        }
    }

    class RecursiveFactorial implements Factorial {
        @Override
        public int get(int n) {
            if (n == 0 || n == 1)
                return 1;
            return n * get(n - 1);
        }
    }

}
