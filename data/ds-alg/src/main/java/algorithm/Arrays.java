package algorithm;

import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.HashMap;
import java.util.HashSet;

import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;
import static util.Algorithm.Complexity.Value.POLYNOMIAL;
import static util.Algorithm.Target.IN_PLACE;
import static util.Algorithm.Target.OUT_OF_PLACE;

class Arrays {

    static int[] find2ThatMultiplyFor(int[] arr, int target) {
        return null;
    }

    static int[] find3ThatMultiplyFor(int[] arr, int target) {
        return null;
    }

    @Algorithm(
            complexity = @Complexity(runtime = LINEAR, space = LINEAR),
            target = OUT_OF_PLACE
    )
    static int[] intersectionForSorted(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null)
            throw new IllegalArgumentException();
        var resultSet = new HashSet<Integer>();
        for (int i = 0, j = 0; i < nums1.length && j < nums2.length;) {
            if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                resultSet.add(nums1[i]);
                i++;
                j++;
            }
        }
        return resultSet.stream().mapToInt(Integer::intValue).toArray();
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

    // MDA

    @Algorithm(complexity = @Complexity(runtime = POLYNOMIAL, space = POLYNOMIAL), target = OUT_OF_PLACE)
    static int[][] rotate2D(int[][] a, int n) {
        var rotated = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                rotated[j][n - 1 - i] = a[i][j];
        return rotated;
    }

    @Algorithm(complexity = @Complexity(runtime = POLYNOMIAL, space = CONSTANT), target = IN_PLACE)
    static int[][] rotate2DInPlace(int[][] arr, int n) {
        // n/2 gives us floor(n/2) and n/2 + n%2 gives us ceiling(n/2)
        for (int i = 0; i < n / 2 + n % 2; i++) {
            for (int j = 0; j < n / 2; j++) {
                int[] tmp = new int[4];
                int currentI = i;
                int currentJ = j;
                for (int k = 0; k < 4; k++) {
                    tmp[k] = arr[currentI][currentJ];
                    int[] newCoordinates = rotateSub(currentI, currentJ, n);
                    currentI = newCoordinates[0];
                    currentJ = newCoordinates[1];
                }
                for (int k = 0; k < 4; k++) {
                    arr[currentI][currentJ] = tmp[(k + 3) % 4];
                    int[] newCoordinates = rotateSub(currentI, currentJ, n);
                    currentI = newCoordinates[0];
                    currentJ = newCoordinates[1];
                }
            }
        }
        return arr;
    }

    private static int[] rotateSub(int i, int j, int n) {
        int[] newCoordinates = new int[2];
        newCoordinates[0] = j;
        newCoordinates[1] = n - 1 - i;
        return newCoordinates;
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
