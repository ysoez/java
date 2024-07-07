package reflection.annotation.discovery.app;

import reflection.annotation.discovery.annotation.InitializerClass;
import reflection.annotation.discovery.annotation.InitializerMethod;

@InitializerClass
public class ConfigsLoader {

    @InitializerMethod
    public void loadAllConfigs() {
        System.out.println("Loading all configuration files");
    }

}
