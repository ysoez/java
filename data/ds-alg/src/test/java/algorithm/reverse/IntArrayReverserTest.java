package algorithm.reverse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntArrayReverserTest {

    @Test
    void reverse() {
        assertThrows(IllegalArgumentException.class, () -> new IntArrayReverser().reverse(null));
        assertArrayEquals(new int[0], new IntArrayReverser().reverse(new int[0]));
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, new IntArrayReverser().reverse(new int[]{1, 2, 3, 4, 5}));
        assertArrayEquals(new int[]{40, 30, 20, 10}, new IntArrayReverser().reverse(new int[]{10, 20, 30, 40}));
    }

}
