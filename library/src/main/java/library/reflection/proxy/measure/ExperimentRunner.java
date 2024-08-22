package library.reflection.proxy.measure;

import library.reflection.proxy.measure.database.DatabaseReader;
import library.reflection.proxy.measure.database.DefaultDatabaseReader;
import library.reflection.proxy.measure.database.UnstableDatabaseReader;
import library.reflection.proxy.measure.http.HttpClient;
import library.reflection.proxy.measure.http.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ExperimentRunner {

    public static void main(String[] args) throws InterruptedException {
        HttpClient httpClient = new DefaultHttpClient();
        useHttpClient(httpClient);

        System.out.println();

        httpClient = TimeMeasuringProxy.newInstance(new DefaultHttpClient());
        useHttpClient(httpClient);

        System.out.println();

        DatabaseReader databaseClient = new DefaultDatabaseReader();
        useDatabaseReader(databaseClient);

        System.out.println();

        databaseClient = TimeMeasuringProxy.newInstance(new DefaultDatabaseReader());
        useDatabaseReader(databaseClient);

        System.out.println();

        List<String> list = TimeMeasuringProxy.newInstance(new ArrayList<>());
        list.add("A");
        list.add("B");
        list.remove("B");

        System.out.println();

        databaseClient = TimeMeasuringProxy.newInstance(new UnstableDatabaseReader());
        useDatabaseReader(databaseClient);
    }

    private static void useHttpClient(HttpClient httpClient) {
        httpClient.initialize();
        String response = httpClient.sendRequest("get data");
        System.out.printf("HTTP: Received response: %s%n", response);
    }

    private static void useDatabaseReader(DatabaseReader databaseReader) throws InterruptedException {
        int rowsInGamesTable;
        String tableName = "Users";
        try {
            rowsInGamesTable = databaseReader.countRowsInTable(tableName);
        } catch (IOException e) {
            System.err.println("Catching exception " + e);
            return;
        }
        System.out.printf("Database: %s table has %s rows %n", tableName, rowsInGamesTable);
        String[] data = databaseReader.readRow("SELECT * from " + tableName);
        System.out.printf("Database: Received result [%s]%n", String.join(" , ", data));
    }

}
