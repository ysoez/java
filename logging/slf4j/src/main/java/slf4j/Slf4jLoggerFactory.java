package slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Slf4jLoggerFactory {

    private static final Logger log = LoggerFactory.getLogger(Slf4jLoggerFactory.class);

    public static void main(String[] args) {
        log.info("hello");
    }

}
