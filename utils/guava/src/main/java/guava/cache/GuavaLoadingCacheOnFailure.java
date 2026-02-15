package guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;

class GuavaLoadingCacheOnFailure {

    public static void main(String[] args) {
        try (var loaderPool = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor())) {
            LoadingCache<String, ListenableFuture<String>> cache = CacheBuilder.newBuilder().build(new CacheLoader<>() {
                @Override
                public ListenableFuture<String> load(String key) {
                    return loaderPool.submit(() -> {
                        if ("bad".equals(key)) {
                            throw new RuntimeException("temporary failure");
                        }
                        return "value-" + key;
                    });
                }
            });
            ListenableFuture<String> future = cache.getUnchecked("bad");
            Futures.addCallback(future, new FutureCallback<>() {
                        public void onSuccess(String v) {
                        }
                        public void onFailure(Throwable t) {
                            System.out.println("load failed, invalidating cache");
                            cache.invalidate("bad");
                        }
                    },
                    MoreExecutors.directExecutor()
            );
            loaderPool.shutdown();
        }
    }

}
