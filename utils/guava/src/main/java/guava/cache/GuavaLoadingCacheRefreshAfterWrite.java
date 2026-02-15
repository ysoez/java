package guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class GuavaLoadingCacheRefreshAfterWrite {

    public static void main(String[] args) throws Exception {
        try (var refreshPool = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor())) {
            LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                    .refreshAfterWrite(2, TimeUnit.SECONDS)
                    .build(new CacheLoader<>() {
                        @Override
                        public String load(String key) throws InterruptedException {
                            System.out.println("load()");
                            Thread.sleep(500);
                            return getValue();
                        }
                        @Override
                        public ListenableFuture<String> reload(String key, String oldVal) {
                            System.out.println("reload()");
                            return refreshPool.submit(this::getValue);
                        }
                        private String getValue() {
                            return "value@" + System.currentTimeMillis();
                        }
                    });
            System.out.println(cache.get("a"));
            Thread.sleep(2000);
            System.out.println(cache.get("a")); // ~ old value (refresh triggered)
            Thread.sleep(1000);
            System.out.println(cache.get("a")); // ~ refreshed value
        }
    }

}
