package reflection.method.polymophism.client;

public class DatabaseClient {

    public boolean persist(String data) {
        System.out.printf("Data : %s was successfully stored in the database%n", data);
        return true;
    }

}
