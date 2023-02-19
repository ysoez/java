package algorithm.reverse;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueueReverserTest {

    @Test
    void reverseQueue() {
        assertArrayEquals(
                new ArrayDeque<>(List.of(30, 20, 10)).toArray(),
                new QueueReverser<>().reverse(new ArrayDeque<>(List.of(10, 20, 30))).toArray()
        );
    }

}