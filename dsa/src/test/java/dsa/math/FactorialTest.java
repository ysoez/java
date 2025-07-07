package dsa.math;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class FactorialTest {

    @ParameterizedTest
    @MethodSource("implementations")
    void test0(Factorial factorial) {
        assertEquals(1, factorial.get(0));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void test1(Factorial factorial) {
        assertEquals(1, factorial.get(1));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void testN(Factorial factorial) {
        assertEquals(2, factorial.get(2));
        assertEquals(6, factorial.get(3));
        assertEquals(24, factorial.get(4));
        assertEquals(120, factorial.get(5));
    }

    @MethodSource
    static Stream<Arguments> implementations() {
        return Stream.of(
                arguments(new Factorial.LoopFactorial()),
                arguments(new Factorial.RecursiveFactorial())
        );
    }

}