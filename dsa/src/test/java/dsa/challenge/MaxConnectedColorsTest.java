package dsa.challenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MaxConnectedColorsTest {

    @Test
    void dsada() {
        char[][] grid = {
                {'r', 'g', 'b'},
                {'r', 'r', 'r'},
                {'g', 'g', 'r'}
        };
        Assertions.assertEquals(5, new MaxConnectedColors.Recursive().get(grid));
    }

}