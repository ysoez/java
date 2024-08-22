package library.reflection.annotation.discovery.app;

import library.reflection.annotation.discovery.annotation.InitializerClass;
import library.reflection.annotation.discovery.annotation.InitializerMethod;

@InitializerClass
public class ConfigsLoader {

    @InitializerMethod
    public void loadAllConfigs() {
        System.out.println("Loading all configuration files");
    }

}
