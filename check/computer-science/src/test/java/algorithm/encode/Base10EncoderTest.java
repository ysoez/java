package algorithm.encode;

import org.junit.jupiter.api.Test;

import static algorithm.encode.Base10Encoder.BASE_62;
import static org.junit.jupiter.api.Assertions.*;

class Base10EncoderTest {

    @Test
    void encode62() {
        assertEquals("ZN9EDCU", BASE_62.encode(2009215674938L));
        System.out.println();
    }

}