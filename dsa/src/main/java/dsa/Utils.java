package dsa;

import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Complexity.CONSTANT;

public class Utils {

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public static <E extends Comparable<E>> E min(E e1, E e2) {
        if (e1 == null)
            return e2;
        if (e2 == null)
            return e1;
        return e1.compareTo(e2) <= 0 ? e1 : e2;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public static <E extends Comparable<E>> E max(E e1, E e2) {
        if (e1 == null)
            return e2;
        if (e2 == null)
            return e1;
        return e1.compareTo(e2) >= 0 ? e1 : e2;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public static <T extends Comparable<T>> boolean lessThan(T a, T b) {
        return a.compareTo(b) < 0;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public static <T extends Comparable<T>> boolean lessThanOrEqual(T a, T b) {
        return a.compareTo(b) <= 0;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public static <T extends Comparable<T>> boolean greaterThan(T a, T b) {
        return a.compareTo(b) > 0;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public static <T extends Comparable<T>> boolean greaterThanOrEqual(T a, T b) {
        return a.compareTo(b) >= 0;
    }

}
