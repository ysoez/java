package guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;

import java.util.concurrent.TimeUnit;

class GuavaCacheRemovalListener {

    public static void main(String[] args) throws Exception {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(1)
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .removalListener((RemovalListener<String, String>) ntf ->
                        System.out.println("removed key=" + ntf.getKey() + ", cause=" + ntf.getCause())
                )
                .build(new CacheLoader<>() {
                    @Override
                    public String load(String key) {
                        return "value-" + key;
                    }
                });
        cache.get("a");
        cache.get("b"); // evicts "a"
        Thread.sleep(3000);
        cache.get("b"); // expires "b"
    }

}
