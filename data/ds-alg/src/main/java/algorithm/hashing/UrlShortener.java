package algorithm.hashing;

import algorithm.encode.Base10Encoder;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

class UrlShortener {

    private static final String BASE_SHORTENER_URL = "https://tinyurl.com/";
    private static final Map<String, String> CACHE = new ConcurrentHashMap<>();

    static String toShortUrl(String originalUrl) {
        String hash = base62Hash(generateId());
        String shortUrl = BASE_SHORTENER_URL + hash;
        putToCache(shortUrl, originalUrl);
        return shortUrl;
    }

    static Optional<String> getOriginalUrl(String shortUrl) {
        return Optional.ofNullable(CACHE.get(shortUrl)).or(() -> findOriginalUrlBy(shortUrl));
    }

    private static Long generateId() {
        return ThreadLocalRandom.current().nextLong( 365_000_000_000L);
    }

    private static String base62Hash(Long value) {
        return Base10Encoder.BASE_62.encode(value);
    }

    private static void putToCache(String shortUrl, String originalUrl) {
        CACHE.put(shortUrl, originalUrl);
    }

    private static Optional<String> findOriginalUrlBy(String shortUrl) {
        return Optional.empty();
    }

}
