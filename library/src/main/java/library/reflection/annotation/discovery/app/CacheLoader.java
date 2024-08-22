package library.reflection.annotation.discovery.app;

import library.reflection.annotation.discovery.annotation.InitializerClass;
import library.reflection.annotation.discovery.annotation.InitializerMethod;

@InitializerClass
public class CacheLoader {

    @InitializerMethod
    public void loadCache() {
        System.out.println("Loading data from cache");
    }

    public void reloadCache() {
        System.out.println("Reload cache");
    }

}
