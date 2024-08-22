package library.reflection.annotation.discovery.app;

import library.reflection.annotation.discovery.annotation.InitializerClass;
import library.reflection.annotation.discovery.annotation.InitializerMethod;

@InitializerClass
public class AutoSaver {

    @InitializerMethod
    public void save() {
        System.out.println("Start automatic data saving to disk");
    }

}
