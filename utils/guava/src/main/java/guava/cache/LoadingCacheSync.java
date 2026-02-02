package guava.cache;

import com.google.common.cache.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class LoadingCacheSync {

    public static void main(String[] args) {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public String load(String key) {
                        System.out.println("loading value for " + key);
                        return "value-for-" + key;
                    }
                    @Override
                    public Map<String, String> loadAll(Iterable<? extends String> keys) {
                        System.out.println("Batch loading: " + keys);
                        Map<String, String> result = new HashMap<>();
                        for (String k : keys) {
                            result.put(k, "value-" + k);
                        }
                        return result;
                    }
                });
        System.out.println(cache.getUnchecked("a")); // ~ load
        System.out.println(cache.getUnchecked("a")); // ~ hit
    }
}
