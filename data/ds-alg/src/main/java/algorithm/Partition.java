package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Partitioning is useful for batching.
 * Check out Lists.partition(java.util.List, int) from Google Guava.
 */
class Partition {

    public static <T> List<List<T>> forList(List<T> list, int batchSize) {
        List<List<T>> batches = new ArrayList<>();
        int i = 0;
        while (i < list.size()) {
            int nextInc = Math.min(list.size() - i, batchSize);
            List<T> batch = list.subList(i, i + nextInc);
            batches.add(batch);
            i = i + nextInc;
        }
        return batches;
    }

}
