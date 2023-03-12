package algorithm;

import util.Algorithm;

import java.util.HashSet;

import static util.Algorithm.Complexity.Value.LINEAR;
import static util.Algorithm.Target.OUT_OF_PLACE;

class Intersection {

    @Algorithm(
        complexity = @Algorithm.Complexity(runtime = LINEAR, space = LINEAR),
        target = OUT_OF_PLACE
    )
    static int[] forSortedArray(int[] nums1, int[] nums2) {
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

    static int[] forUnsortedIntersection(int[] nums1, int[] nums2) {
        return null;
    }

}
