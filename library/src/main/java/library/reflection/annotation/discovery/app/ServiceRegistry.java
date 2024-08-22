package library.reflection.annotation.discovery.app;

import library.reflection.annotation.discovery.annotation.InitializerClass;
import library.reflection.annotation.discovery.annotation.InitializerMethod;

@InitializerClass
public class ServiceRegistry {

    @InitializerMethod
    public void registerService() {
        System.out.println("Service successfully registered");
    }

}
