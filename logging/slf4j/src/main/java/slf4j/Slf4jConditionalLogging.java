package slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Slf4jConditionalLogging {

    private static final Logger log = LoggerFactory.getLogger(Slf4jConditionalLogging.class);

    public static void main(String[] args) {
        if (log.isDebugEnabled()) {
            log.debug("log: {}", computeHeavyData());
        }
        log.atDebug().log(() -> "lazy log: " + computeHeavyData());
    }

    private static String computeHeavyData() {
        return "expensive debug";
    }

}
