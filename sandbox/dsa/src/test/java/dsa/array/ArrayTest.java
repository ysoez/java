package dsa.array;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ArrayTest {

    @ParameterizedTest
    @MethodSource("implementations")
    void testSetAndGet(Array<Integer> array) {
        array.set(0, 10);
        array.set(1, 100);
        assertEquals(10, array.get(0));
        assertEquals(100, array.get(1));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void testGetWithInvalidIndex(Array<Integer> array) {
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(array.length()));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void testSetWithInvalidIndex(Array<Integer> array) {
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(-1, 100));
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(array.length(), 100));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void testSetNull(Array<Integer> array) {
        array.set(0, null);
        assertNull(array.get(0));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void testSetOverwrite(Array<Integer> array) {
        array.set(0, 100);
        assertEquals(100, array.get(0));

        array.set(0, 200);
        assertEquals(200, array.get(0));
    }

    @ParameterizedTest
    @MethodSource("emptyImplementations")
    void testSetIfEmpty(Array<Integer> array) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.set(0, 100));
    }

    @ParameterizedTest
    @MethodSource("emptyImplementations")
    void testGetIfEmpty(Array<Integer> array) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.get(0));
    }

    @ParameterizedTest
    @MethodSource("emptyImplementations")
    void testLength(Array<Integer> array) {
        assertEquals(0, array.length());
    }

    @MethodSource
    static Stream<Arguments> implementations() {
        return Stream.of(
                arguments(new StaticArray<>()),
                arguments(new DynamicArray<>())
        );
    }

    @MethodSource
    static Stream<Arguments> emptyImplementations() {
        return Stream.of(
                arguments(new StaticArray<>(0)),
                arguments(new DynamicArray<>(0))
        );
    }

}