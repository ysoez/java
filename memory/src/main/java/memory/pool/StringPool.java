package memory.pool;

import java.util.ArrayList;
import java.util.Date;

class StringPool {

    // ~ -XX:+PrintStringTableStatistics
    // ~ -XX:StringTableSize=10785
    // ~ -XX:MaxHeapSize=200m (OOM)
    public static void main(String[] args) {
        var list = new ArrayList<String>();
        var start = new Date();
        for (int i = 0; i < 30_000_000; i++) {
            String str = Integer.valueOf(i).toString().intern();
            list.add(str);
        }
        var end = new Date();
        System.out.printf("Elapsed time: %s ms\n\n", end.getTime() - start.getTime());
    }

}
