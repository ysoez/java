package dsa.math;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class FibTest {

    @ParameterizedTest
    @MethodSource("baseConditionsSource")
    void testBaseConditions(Fib fib, int in, int out) {
        assertEquals(out, fib.get(in));
    }

    @ParameterizedTest
    @MethodSource("happyPathSource")
    void testHappyPath(Fib fib, int in, int out) {
        assertEquals(out, fib.get(in));
    }

    @ParameterizedTest
    @MethodSource("sequenceConsistencySource")
    void testSequenceConsistency(Fib fib, int n, int n1, int n2) {
        assertEquals(fib.get(n), fib.get(n1) + fib.get(n2));
    }

    @ParameterizedTest
    @MethodSource("negativeInputSource")
    void testNegativeInput(Fib fib) {
        assertThrows(IllegalArgumentException.class, () -> fib.get(-1));
    }

    @MethodSource
    static Stream<Arguments> baseConditionsSource() {
        return Stream.of(
                arguments(new Fib.RecursiveFib(), 0, 0),
                arguments(new Fib.RecursiveFib(), 1, 1),
                arguments(new Fib.IterativeFib(), 0, 0),
                arguments(new Fib.IterativeFib(), 1, 1)
        );
    }

    @MethodSource
    static Stream<Arguments> happyPathSource() {
        return Stream.of(
                arguments(new Fib.RecursiveFib(), 2, 1),
                arguments(new Fib.RecursiveFib(), 3, 2),
                arguments(new Fib.RecursiveFib(), 5, 5),
                arguments(new Fib.RecursiveFib(), 10, 55),
                arguments(new Fib.IterativeFib(), 2, 1),
                arguments(new Fib.IterativeFib(), 3, 2),
                arguments(new Fib.IterativeFib(), 5, 5),
                arguments(new Fib.IterativeFib(), 10, 55)
        );
    }

    @MethodSource
    static Stream<Arguments> sequenceConsistencySource() {
        return Stream.of(
                arguments(new Fib.RecursiveFib(), 5, 4, 3),
                arguments(new Fib.RecursiveFib(), 7, 6, 5),
                arguments(new Fib.IterativeFib(), 5, 4, 3),
                arguments(new Fib.IterativeFib(), 7, 6, 5)
        );
    }

    @MethodSource
    static Stream<Arguments> negativeInputSource() {
        return Stream.of(
                arguments(new Fib.RecursiveFib(), -1),
                arguments(new Fib.IterativeFib(), -1)
        );
    }

}