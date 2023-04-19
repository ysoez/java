package proxy;

import org.junit.jupiter.api.Test;
import proxy.orm.DbContext;

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

    @Test
    void ormTest() {
        var dbContext = new DbContext();
        var product = dbContext.getProduct(1);
        product.setName("Updated Name");
        dbContext.saveChanges();

        product.setName("Another name");
        dbContext.saveChanges();
    }

}