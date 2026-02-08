package library.async.future.completable;

import java.util.concurrent.CompletableFuture;

class CompletableFutureCombine {

    public static void main(String[] args) {
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> 20);
        CompletableFuture<Integer> combined = f1.thenCombine(f2, Integer::sum);
        System.out.println(combined.join());
    }

}
