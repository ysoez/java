package persistence.hibernate.logging;

import lombok.Getter;
import net.ttddyy.dsproxy.QueryCount;
import net.ttddyy.dsproxy.QueryCountHolder;

public class SQLStatementCountValidator {

    private SQLStatementCountValidator() {
    }

    public static void reset() {
        QueryCountHolder.clear();
    }

    public static void assertSelectCount(int expectedSelectCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long recordedSelectCount = queryCount.getSelect();
        if (expectedSelectCount != recordedSelectCount) {
            throw new SQLStatementCountMismatchException(expectedSelectCount, recordedSelectCount);
        }
    }

    public static void assertInsertCount(int expectedInsertCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long recordedInsertCount = queryCount.getInsert();
        if (expectedInsertCount != recordedInsertCount) {
            throw new SQLStatementCountMismatchException(expectedInsertCount, recordedInsertCount);
        }
    }

    public static void assertUpdateCount(int expectedUpdateCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long recordedUpdateCount = queryCount.getUpdate();
        if (expectedUpdateCount != recordedUpdateCount) {
            throw new SQLStatementCountMismatchException(expectedUpdateCount, recordedUpdateCount);
        }
    }

    public static void assertDeleteCount(int expectedDeleteCount) {
        QueryCount queryCount = QueryCountHolder.getGrandTotal();
        long recordedDeleteCount = queryCount.getDelete();
        if (expectedDeleteCount != recordedDeleteCount) {
            throw new SQLStatementCountMismatchException(expectedDeleteCount, recordedDeleteCount);
        }
    }

    @Getter
    static class SQLStatementCountMismatchException extends RuntimeException {

        private final long expected;
        private final long recorded;

        public SQLStatementCountMismatchException(long expected, long recorded) {
            super(String.format("Expected %d statement(s) but recorded %d instead!", expected, recorded));
            this.expected = expected;
            this.recorded = recorded;
        }
    }

}
