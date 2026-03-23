package slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Slf4jExceptionLogging {

    private static final Logger log = LoggerFactory.getLogger(Slf4jExceptionLogging.class);

    public static void main(String[] args) {
        try {
            int result = 10 / 0;
        } catch (Exception e) {
            log.error("calculation failed", e);
        }
    }

}
