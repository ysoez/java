package algorithm;

import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.HashMap;

import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;

class Arrays {

    static int[] find2ThatMultiplyFor(int[] arr, int target) {
        return null;
    }

    static int[] find3ThatMultiplyFor(int[] arr, int target) {
        return null;
    }

    // no duplicates
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    static boolean isRotation(int[] first, int[] second) {
        if (!(first != null && second != null && first.length == second.length))
            return false;
        int secondIdx = -1;
        for (int i = 0; i < second.length; i++)
            if (second[i] == first[0]) {
                secondIdx = i;
                break;
            }
        if (secondIdx == -1)
            return false;
        for (int i = 0; i < first.length; i++) {
            int j = (secondIdx + i) % first.length;
            if (first[i] != second[j])
                return false;
        }
        return true;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    static Integer mostFrequent(int[] arr) {
        Integer count = 0;
        Integer mostFrequent = null;
        var frequency = new HashMap<Integer, Integer>();
        for (int i : arr) {
            frequency.put(i, frequency.getOrDefault(i, 0) + 1);
            if (frequency.get(i) > count) {
                count = frequency.get(i);
                mostFrequent = i;
            }
        }
        return mostFrequent;
    }

    static int[][] mineSweeper(int[][] bombs, int numRows, int numCols) {
        int[][] field = new int[numRows][numCols];
        for (int[] bomb : bombs) {
            int rowIndex = bomb[0];
            int colIndex = bomb[1];
            field[rowIndex][colIndex] = -1;
            for (int i = rowIndex - 1; i < rowIndex + 2; i++) {
                for (int j = colIndex - 1; j < colIndex + 2; j++) {
                    if (0 <= i && i < numRows && 0 <= j && j < numCols && field[i][j] != -1) {
                        field[i][j] += 1;
                    }
                }
            }
        }
        return field;
    }

}
