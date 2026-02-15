package guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.*;

import java.util.concurrent.Executors;

class GuavaLoadingCacheAsync {

    public static void main(String[] args) {
        try (var poolLoader = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(2))) {
            LoadingCache<String, ListenableFuture<String>> cache = CacheBuilder.newBuilder()
                    .maximumSize(100)
                    .build(new CacheLoader<>() {
                        @Override
                        public ListenableFuture<String> load(String key) {
                            System.out.println("Loading " + key + " on " + Thread.currentThread().getName());
                            return poolLoader.submit(() -> {
                                Thread.sleep(500);
                                return "value-for-" + key;
                            });
                        }
                    });
            ListenableFuture<String> f1 = cache.getUnchecked("a"); // ~ load async
            ListenableFuture<String> f2 = cache.getUnchecked("a"); // ~ hit
            System.out.println("same future: " + (f1 == f2));
            Futures.addCallback(f1, new FutureCallback<>() {
                        public void onSuccess(String v) {
                            System.out.println("result: " + v);
                        }
                        public void onFailure(Throwable t) {
                            System.err.println("failed:" + t.getMessage());
                        }
                    },
                    MoreExecutors.directExecutor()
            );
        }
    }

}
