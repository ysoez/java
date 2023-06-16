package algorithm.hashing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlShortenerTest {

    @Test
    void toShortUrl() {
        String shortUrl = UrlShortener.toShortUrl("https://en.wikipedia.org/wiki/Systems_design");
        assertTrue(shortUrl.contains("https://tinyurl.com/"));
        assertEquals(7, shortUrl.substring(20).length());
    }

    @Test
    void getOriginalUrl() {
        assertTrue(UrlShortener.getOriginalUrl("https://tinyurl.com//abcde").isEmpty());

        String originalUrl = "https://en.wikipedia.org/wiki/Systems_design";
        String shortUrl = UrlShortener.toShortUrl(originalUrl);

        assertTrue(UrlShortener.getOriginalUrl(shortUrl).isPresent());
        assertEquals(originalUrl, UrlShortener.getOriginalUrl(shortUrl).get());
    }

}