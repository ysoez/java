package dsa.challenge;

import java.util.*;

interface MaxConnectedColors {

    int get(char[][] grid);

    class Recursive implements MaxConnectedColors {
        @Override
        public int get(char[][] grid) {
            System.out.println(Arrays.deepToString(grid));
            int maxCount = 0;
            Map<String, Boolean> seen = new HashMap<>();
            //
            // ~ rows
            //
            for (int y = 0; y < grid.length; y++) {
                //
                // ~
                //
                for (int x = 0; x < grid[y].length; x++) {
                    int count = traverse(grid, x, y, seen);
                    maxCount = Math.max(count, maxCount);
                }
            }
            return maxCount;
        }

        private int traverse(char[][] grid, int x, int y, Map<String, Boolean> seen) {
            return traverseHelper(grid, x, y, 0, seen);
        }

        private int traverseHelper(char[][] grid, int x, int y, int count, Map<String, Boolean> seen) {
            var key = String.format("%s,%s", x, y);
            if (seen.get(key) != null)
                return 0;
            seen.put(key, true);

            var color = grid[y][x];
            var sum = 1;
            for (int[] point : getNeighbors(grid, x, y)) {
                var i = point[0];
                var j = point[1];
                if (grid[j][i] != color)
                    continue;
                seen.put(key, true);
                sum += traverseHelper(grid, i, j, 1, seen);
            }
            return sum;
        }
    }

    static List<int[]> getNeighbors(char[][] grid, int x, int y) {
        List<int[]> coords = new ArrayList<>();
        int[][] neighbors = {
                {-1, 0},
                {0, -1},
                {1, 0},
                {0, 1}
        };
        for (int[] n : neighbors) {
            int coordX = x + n[0];
            int coordY = y + n[1];
            if (coordX < 0 || coordY < 0 || coordY >= grid.length || coordX >= grid[0].length) {
                continue;
            }
            coords.add(new int[]{coordX, coordY});
        }
        return coords;
    }

}
