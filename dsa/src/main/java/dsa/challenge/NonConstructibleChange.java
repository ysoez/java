package dsa.challenge;

import dsa.Algorithm;

import java.util.Arrays;

import static dsa.Algorithm.Complexity.Value.CONSTANT;
import static dsa.Algorithm.Complexity.Value.LINEARITHMIC;

class NonConstructibleChange {

    @Algorithm(complexity = @Algorithm.Complexity(runtime = LINEARITHMIC, space = CONSTANT))
    static int getMin(int[] coins) {
        Arrays.sort(coins);
        var currentChange = 0;
        for (int coin : coins) {
            if (coin > currentChange + 1)
                return currentChange + 1;
            currentChange += coin;
        }
        return currentChange + 1;
    }

}
