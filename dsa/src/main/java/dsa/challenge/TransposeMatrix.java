package dsa.challenge;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.function.UnaryOperator;

import static dsa.Algorithm.Target.OUT_OF_PLACE;
import static dsa.array.Arrays.EMPTY_2D_INT_ARR;
import static dsa.array.Arrays.isEmpty;

class TransposeMatrix implements UnaryOperator<int[][]> {

    @Override
    @Algorithm(complexity = @Complexity(runtime = "O(w * h)", space = "O(w * h)"), target = OUT_OF_PLACE)
    public int[][] apply(int[][] matrix) {
        if (isEmpty(matrix))
            return EMPTY_2D_INT_ARR;
        var out = new int[matrix[0].length][matrix.length];
        for (int col = 0; col < matrix[0].length; col++)
            for (int row = 0; row < matrix.length; row++)
                out[col][row] = matrix[row][col];
        return out;
    }

}
