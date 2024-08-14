package library.concurrency.lock.rw;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ProductReviewsService {
    private final HashMap<Integer, List<String>> productIdToReviews = new HashMap<>();
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = reentrantReadWriteLock.readLock();
    private final Lock writeLock = reentrantReadWriteLock.writeLock();

    public void addProduct(int productId) {
        writeLock.lock();
        try {
            if (!productIdToReviews.containsKey(productId)) {
                productIdToReviews.put(productId, new ArrayList<>());
            }
        } finally {
            writeLock.unlock();
        }
    }

    public void removeProduct(int productId) {
        writeLock.lock();
        try {
            productIdToReviews.remove(productId);
        } finally {
            writeLock.unlock();
        }
    }

    public void addProductReview(int productId, String review) {
        writeLock.lock();
        try {
            if (!productIdToReviews.containsKey(productId)) {
                productIdToReviews.put(productId, new ArrayList<>());
            }
            productIdToReviews.get(productId).add(review);
        } finally {
            writeLock.unlock();
        }
    }

    public List<String> getAllProductReviews(int productId) {
        readLock.lock();
        try {
            if (productIdToReviews.containsKey(productId)) {
                return Collections.unmodifiableList(productIdToReviews.get(productId));
            }
        } finally {
            readLock.unlock();
        }
        return Collections.emptyList();
    }

    public Optional<String> getLatestReview(int productId) {
        readLock.lock();
        try {
            if (productIdToReviews.containsKey(productId) && !productIdToReviews.get(productId).isEmpty()) {
                List<String> reviews = productIdToReviews.get(productId);
                return Optional.of(reviews.get(reviews.size() - 1));
            }
        } finally {
            readLock.unlock();
        }
        return Optional.empty();
    }

    public Set<Integer> getAllProductIdsWithReviews() {
        readLock.lock();
        try {
            Set<Integer> productsWithReviews = new HashSet<>();
            for (Map.Entry<Integer, List<String>> productEntry : productIdToReviews.entrySet()) {
                if (!productEntry.getValue().isEmpty()) {
                    productsWithReviews.add(productEntry.getKey());
                }
            }
            return productsWithReviews;
        } finally {
            readLock.unlock();
        }
    }

}