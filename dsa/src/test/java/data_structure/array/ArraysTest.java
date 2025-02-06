package data_structure.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArraysTest {

    @Test
    void max() {
        assertThrows(IllegalArgumentException.class, () -> Arrays.max(null));
        assertThrows(EmptyArrayException.class, () -> Arrays.max(new int[0]));

        assertEquals(1, Arrays.max(new int[]{1}));

        assertEquals(2, Arrays.max(new int[]{1, 2}));
        assertEquals(2, Arrays.max(new int[]{2, 1}));

        assertEquals(78, Arrays.max(new int[]{10, 21, 16, 78, 62, 5}));
        assertEquals(30, Arrays.max(new int[]{10, 20, 30}));
    }

}