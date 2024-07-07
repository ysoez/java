package reflection.annotation.discovery.app;

import reflection.annotation.discovery.annotation.InitializerClass;
import reflection.annotation.discovery.annotation.InitializerMethod;

@InitializerClass
public class ServiceRegistry {

    @InitializerMethod
    public void registerService() {
        System.out.println("Service successfully registered");
    }

}
