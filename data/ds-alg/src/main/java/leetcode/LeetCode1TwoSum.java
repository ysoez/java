package leetcode;

import java.util.HashMap;

public class LeetCode1TwoSum {

    static int[] find(int[] nums, int target) {
        var map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
//            map.put(nums[i], i);
            int other = target - nums[i];
            if (map.containsKey(other)) {
                return new int[]{map.get(other), i};
            }
            map.put(nums[i], i);
        }
        return nums;
    }

}
