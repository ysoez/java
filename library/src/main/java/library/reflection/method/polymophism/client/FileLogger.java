package library.reflection.method.polymophism.client;

import java.io.IOException;

public class FileLogger {

    public void log(String data) throws IOException {
//        throw new IOException("Failed saving request to a file");
        System.out.printf("Data : %s was logged to the file system%n", data);
    }

}
