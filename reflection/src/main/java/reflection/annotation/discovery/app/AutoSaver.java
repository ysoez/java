package reflection.annotation.discovery.app;

import reflection.annotation.discovery.annotation.InitializerClass;
import reflection.annotation.discovery.annotation.InitializerMethod;

@InitializerClass
public class AutoSaver {

    @InitializerMethod
    public void save() {
        System.out.println("Start automatic data saving to disk");
    }

}
