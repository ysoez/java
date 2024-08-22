package library.reflection.proxy.measure.database;

import java.io.IOException;

public final class UnstableDatabaseReader implements DatabaseReader {

    @Override
    public int countRowsInTable(String tableName) throws IOException {
        throw new IOException("network issue");
    }

    @Override
    public String[] readRow(String sqlQuery) throws InterruptedException {
        throw new UnsupportedOperationException("network issue");
    }

}
