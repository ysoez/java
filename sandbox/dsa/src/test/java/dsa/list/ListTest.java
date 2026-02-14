package dsa.list;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListTest {

    @Nested
    class InsertFirst {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void empty(List<Integer> list) {
            list.insertFirst(10);
            assertEquals(1, list.size());
            assertEquals(10, list.getFirst());
        }
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void nonEmpty(List<Integer> list) {
            list.insertLast(20);
            list.insertFirst(10);
            assertEquals(2, list.size());
            assertEquals(10, list.getFirst());
            assertEquals(20, list.getLast());
        }
    }

    @Nested
    class InsertAt {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void emptyAtZero(List<Integer> list) {
            list.insertAt(0, 10);
            assertEquals(10, list.getFirst());
        }
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void singleAtStart(List<Integer> list) {
            list.insertLast(20);
            list.insertAt(0, 10);
            assertEquals(10, list.getFirst());
            assertEquals(20, list.getLast());
        }
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void singleAtEnd(List<Integer> list) {
            list.insertLast(10);
            list.insertAt(1, 20);
            assertEquals(20, list.getLast());
        }
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void middle(List<Integer> list) {
            list.insertLast(10);
            list.insertLast(30);
            list.insertAt(1, 20);
            assertEquals(20, list.get(1));
        }
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void invalidIndices(List<Integer> list) {
            assertThrows(IndexOutOfBoundsException.class, () -> list.insertAt(-1, 1));
            assertThrows(IndexOutOfBoundsException.class, () -> list.insertAt(1, 1));
        }
    }

    @Nested
    class InsertLast {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void empty(List<Integer> list) {
            list.insertLast(10);
            assertEquals(10, list.getLast());
        }
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void nonEmpty(List<Integer> list) {
            list.insertLast(10);
            list.insertLast(20);
            assertEquals(20, list.getLast());
            assertEquals(2, list.size());
        }
    }

    @Nested
    class Set {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void empty(List<Integer> list) {
            assertThrows(IndexOutOfBoundsException.class, () -> list.set(0, 10));
        }
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void single(List<Integer> list) {
            list.insertLast(10);
            list.set(0, 11);
            assertEquals(1, list.size());
            assertEquals(11, list.getFirst());
        }
    }

    @Nested
    class Get {
        // todo:.......
    }

    @Nested
    class Query {
        // todo: isempty etc
    }

    @Nested
    class DeleteFirst {

    }

    @Nested
    class DeleteAt {
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void empty(List<Integer> list) {
            assertThrows(NoSuchElementException.class, () -> list.deleteAt(0));
        }
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void single(List<Integer> list) {
            list.insertFirst(10);
            assertEquals(10, list.deleteAt(0));
        }
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void nonEmptyAtStart(List<Integer> list) {
            list.insertLast(10);
            list.insertLast(20);
            assertEquals(10, list.deleteAt(0));
        }
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void nonEmptyAtEnd(List<Integer> list) {
            list.insertLast(10);
            list.insertLast(20);

            var removed = list.deleteAt(1);

            assertEquals(1, list.size());
            assertEquals(20, removed);
        }
        @ParameterizedTest
        @MethodSource("dsa.list.ListTest#implementations")
        void middle(List<Integer> list) {
            list.insertLast(10);
            list.insertLast(20);
            list.insertLast(30);

            assertEquals(20, list.deleteAt(1));

            assertEquals(2, list.size());
            assertEquals(10, list.getFirst());
            assertEquals(20, list.getLast());
        }
    }

    @Nested
    class DeleteLast {

    }

    @Nested
    class Utility {
        @Test
        void clear() {

        }
        @Test
        void toArray() {

        }
    }
    
    @MethodSource
    static Stream<Arguments> implementations() {
        return Stream.of(
//                arguments(new SinglyLinkedList<Integer>())
        );
    }

//    public abstract class ListTest {
//
//        private final List<Integer> list = newList();
//
//        public abstract List<Integer> newList();
//
//
//
//        @Test
//        void testInsertLast() {
//            list.insertLast(1);
//            list.insertLast(2);
//            list.insertLast(3);
//
//            assertEquals(3, list.size());
//            assertEquals(3, list.get(2));
//        }
//
//        @Test
//        void testInsertAtMiddle() {
//            list.insertLast(1);
//            list.insertLast(3);
//
//            list.insertAt(1, 2);
//
//            assertEquals(3, list.size());
//            assertEquals(2, list.get(1));
//        }
//
//        @Test
//        void testDeleteFirst() {
//            list.insertLast(10);
//            list.insertLast(20);
//
//            var deleted = list.deleteFirst();
//
//            assertEquals(1, list.size());
//            assertEquals(10, deleted);
//        }
//
//        @Test
//        void testDeleteLast() {
//            list.insertLast(100);
//            list.insertLast(200);
//
//            var deleted = list.deleteLast();
//
//            assertEquals(1, list.size());
//            assertEquals(200, deleted);
//        }
//
//        @Test
//        void testDeleteAtIndex() {
//            list.insertLast(10);
//            list.insertLast(20);
//            list.insertLast(30);
//            list.insertLast(40);
//
//            assertEquals(10, list.deleteAt(0));;
//            assertEquals(20, list.get(0));
//            assertEquals(3, list.size());
//
//            assertEquals(30, list.deleteAt(1));
//            assertEquals(20, list.get(0));
//            assertEquals(40, list.get(1));
//
//            assertEquals(20, list.deleteAt(0));
//            assertEquals(40, list.get(0));
//
//            assertEquals(40, list.deleteAt(0));
//            assertTrue(list.isEmpty());
//        }
//
//        @Test
//        void testDeleteAtInvalidIndex() {
//            list.insertLast(1);
//            assertThrows(IndexOutOfBoundsException.class, () -> list.deleteAt(-1));
//            assertThrows(IndexOutOfBoundsException.class, () -> list.deleteAt(1));
//        }
//
//        @Test
//        void testDeleteFromEmptyList() {
//            assertThrows(NoSuchElementException.class, list::deleteFirst);
//            assertThrows(NoSuchElementException.class, list::deleteLast);
//        }
//
//        @Test
//        void testIndexOfAndContains() {
//            list.insertLast(42);
//            list.insertLast(99);
//
//            assertEquals(0, list.indexOf(42));
//            assertEquals(1, list.indexOf(99));
//            assertEquals(-1, list.indexOf(Integer.MAX_VALUE));
//
//            assertTrue(list.contains(42));
//            assertFalse(list.contains(7));
//            assertFalse(list.contains(Integer.MAX_VALUE));
//        }
//
//        @Test
//        void testIsEmpty() {
//            assertTrue(list.isEmpty());
//            list.insertLast(1);
//            assertFalse(list.isEmpty());
//            list.deleteFirst();
//            assertTrue(list.isEmpty());
//        }
//
//        @Test
//        void testReverse() {
//            list.insertLast(1);
//            list.insertLast(2);
//            list.insertLast(3);
//
//            list.reverse();
//
//            assertEquals(3, list.get(0));
//            assertEquals(2, list.get(1));
//            assertEquals(1, list.get(2));
//        }
//
//        @Test
//        void testGetFromEnd() {
//            list.insertLast(1);
//            list.insertLast(2);
//            list.insertLast(3);
//
//            assertEquals(3, list.getFromEnd(-2));
//            assertEquals(3, list.getFromEnd(-1));
//            assertEquals(3, list.getFromEnd(0));
//            assertEquals(3, list.getFromEnd(1));
//            assertEquals(2, list.getFromEnd(2));
//            assertEquals(1, list.getFromEnd(3));
//
//            assertThrows(IllegalArgumentException.class, () -> list.getFromEnd(4));
//        }
//
//    }

}