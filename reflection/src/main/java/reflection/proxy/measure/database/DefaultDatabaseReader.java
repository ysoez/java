package reflection.proxy.measure.database;

public final class DefaultDatabaseReader implements DatabaseReader {

    @Override
    public int countRowsInTable(String tableName) throws InterruptedException {
        System.out.printf("Database: Counting rows in table [%s]%n", tableName);
        Thread.sleep(1000);
        return 50;
    }

    @Override
    public String[] readRow(String sqlQuery) throws InterruptedException {
        System.out.printf("Database: Executing SQL query [%s]%n", sqlQuery);
        Thread.sleep(1500);
        return new String[]{"column1", "column2", "column3"};
    }

}
