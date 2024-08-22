package library.reflection.proxy.measure.database;

import java.io.IOException;

public interface DatabaseReader {

    int countRowsInTable(String tableName) throws InterruptedException, IOException;

    String[] readRow(String sqlQuery) throws InterruptedException;
}
