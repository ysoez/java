package slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

class Slf4jMdc {

    private static final Logger log = LoggerFactory.getLogger(Slf4jMdc.class);

    public static void main(String[] args) {
        try {
            MDC.put("userId", "111");
            log.info("{} has logged in", MDC.get("userId"));
        } finally {
            MDC.clear();
        }

    }

}
