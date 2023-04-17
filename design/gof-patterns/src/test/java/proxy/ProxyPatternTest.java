package proxy;

import org.junit.jupiter.api.Test;

class ProxyPatternTest {

    @Test
    void test() {
        var library = new Library();
        for (String file : new String[]{"a", "b", "c"}) {
            library.add(new ProxyEbook(file));
        }
        library.open("a");
        library.open("b");
    }

}