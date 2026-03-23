package slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Slf4jLogLevels {

    private static final Logger log = LoggerFactory.getLogger(Slf4jLogLevels.class);

    public static void main(String[] args) {
        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }

}
