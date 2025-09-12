package dsa.math;

interface Fib {

    int get(int val);

    class RecursiveFib implements Fib {
        @Override
        public int get(int val) {
            if (val == 0)
                return 0;
            if (val == 1)
                return 1;
            return get(val - 1) + get(val - 2);
        }
    }

    class IterativeFib implements Fib {
        @Override
        public int get(int val) {
            return 0;
        }
    }

}
