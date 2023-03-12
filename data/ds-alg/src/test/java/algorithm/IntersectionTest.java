package algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntersectionTest {

    @Test
    void sortedArrayIntersection() {
        assertArrayEquals(new int[]{2}, Intersection.forSortedArray(new int[]{1, 2, 2}, new int[]{2, 2}));
        assertArrayEquals(new int[]{3, 7}, Intersection.forSortedArray(new int[]{3, 5, 7}, new int[]{3, 7, 8, 9}));
    }

    @Test
    void unsortedArrayIntersection() {
        assertArrayEquals(new int[]{2}, Intersection.forUnsortedIntersection(new int[]{1, 2, 2, 1}, new int[]{2, 2}));
        assertArrayEquals(new int[]{9, 4}, Intersection.forUnsortedIntersection(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4}));
    }

}