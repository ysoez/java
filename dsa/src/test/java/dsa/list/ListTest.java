package dsa.list;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ListTest {

    // REVIEW TESTTS

    @Nested
    class InsertFirstTests {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void testInsertFirst_AddsElementAtStart(List<String> list) {
            list.insertFirst("A");
            assertEquals(0, list.indexOf("A"));
        }

        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void testInsertFirstOnEmptyList(List<String> list) {
            assertTrue(list.isEmpty());
            list.insertFirst("X");
            assertFalse(list.isEmpty());
        }

        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void insertFirstMultipleTimes(List<String> list) {
            list.insertFirst("C");
            list.insertFirst("B");
            list.insertFirst("A");
            assertEquals(0, list.indexOf("A"));
            assertEquals(3, list.size());
        }
    }

    @Nested
    class InsertLastTests {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void insertLast_AppendsElement(List<String> list) {
            list.insertLast("A");
            list.insertLast("B");
            assertEquals(1, list.indexOf("B"));
        }

        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void insertLast_OnEmptyList(List<String> list) {
            list.insertLast("X");
            assertEquals(0, list.indexOf("X"));
        }
    }

    @Nested
    class InsertAtTests {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void insertAt_ValidIndex(List<String> list) {
            list.insertLast("A");
            list.insertLast("C");
            list.insertAt(1, "B");
            assertEquals("B", list.deleteAt(1));
        }

        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void insertAt_AtStart(List<String> list) {
            list.insertLast("B");
            list.insertAt(0, "A");
            assertEquals(0, list.indexOf("A"));
        }

        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void insertAt_AtEnd(List<String> list) {
            list.insertLast("A");
            list.insertAt(list.size(), "B");
            assertEquals(1, list.indexOf("B"));
        }

        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void insertAt_InvalidIndexThrows(List<String> list) {
            assertThrows(IndexOutOfBoundsException.class, () -> list.insertAt(1, "X"));
        }
    }

    @Nested
    class DeleteFirstTests {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void deleteFirst_RemovesFirst(List<String> list) {
            list.insertLast("A");
            list.insertLast("B");
            assertEquals("A", list.deleteFirst());
        }

        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void deleteFirst_OnEmptyListThrows(List<String> list) {
            assertThrows(IndexOutOfBoundsException.class, list::deleteFirst);
        }
    }

    @Nested
    class DeleteLastTests {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void deleteLast_RemovesLast(List<String> list) {
            list.insertLast("A");
            list.insertLast("B");
            assertEquals("B", list.deleteLast());
        }

        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void deleteLast_OnEmptyListThrows(List<String> list) {
            assertThrows(IndexOutOfBoundsException.class, list::deleteLast);
        }
    }

    @Nested
    class DeleteAtTests {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void deleteAt_RemovesElementAtIndex(List<String> list) {
            list.insertLast("A");
            list.insertLast("B");
            assertEquals("B", list.deleteAt(1));
        }

        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void deleteAt_InvalidIndexThrows(List<String> list) {
            list.insertLast("A");
            assertThrows(IndexOutOfBoundsException.class, () -> list.deleteAt(2));
        }
    }

    @Nested
    class SizeTests {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void size_ReturnsCorrectValue(List<String> list) {
            assertEquals(0, list.size());
            list.insertLast("A");
            assertEquals(1, list.size());
        }
    }

    @Nested
    class IsEmptyTests {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void isEmpty_OnEmptyListTrue(List<String> list) {
            assertTrue(list.isEmpty());
        }

        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void isEmpty_AfterInsertFalse(List<String> list) {
            list.insertLast("A");
            assertFalse(list.isEmpty());
        }
    }

    @Nested
    class IndexOfTests {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void indexOf_Found(List<String> list) {
            list.insertLast("A");
            assertEquals(0, list.indexOf("A"));
        }

        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void indexOf_NotFound(List<String> list) {
            list.insertLast("A");
            assertEquals(-1, list.indexOf("B"));
        }
    }

    static Stream<Arguments> implementations() {
        return Stream.of(
                Arguments.arguments(new ArrayList<String>())
        );
    }


}