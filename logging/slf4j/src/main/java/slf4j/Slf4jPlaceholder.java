package slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Slf4jPlaceholder {

    private static final Logger log = LoggerFactory.getLogger(Slf4jPlaceholder.class);

    public static void main(String[] args) {
        log.debug("hello {}", "world");
    }

}
