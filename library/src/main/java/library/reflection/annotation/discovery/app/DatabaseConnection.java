package library.reflection.annotation.discovery.app;

import library.reflection.annotation.discovery.annotation.InitializerClass;
import library.reflection.annotation.discovery.annotation.InitializerMethod;

@InitializerClass
public class DatabaseConnection {

    @InitializerMethod
    public void connectToDatabase1() {
        System.out.println("Connecting to database 1");
    }

    @InitializerMethod
    public void connectToDatabase2() {
        System.out.println("Connecting to database 2");
    }

}
