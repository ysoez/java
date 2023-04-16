package decorator;

import org.junit.jupiter.api.Test;

class DecoratorPatternTest {

    @Test
    void test() {
        new CompressedStream(
                new EncryptedStream(
                        new CloudStream()
                )
        ).write("1234-1234-1234-1234");
    }

}