package apache.collection;

import org.apache.commons.collections4.map.LRUMap;

class ApacheLruMap {
    
    public static void main(String[] args) {
        LRUMap<String, String> cache = new LRUMap<>(3);
        cache.put("A", "1");
        cache.put("B", "2");
        cache.put("C", "3");
        cache.put("D", "4"); // ~ "A" gets evicted
        System.out.println(cache.get("A"));
    }

}
