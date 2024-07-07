package reflection.annotation.discovery;

import reflection.annotation.discovery.annotation.InitializerClass;
import reflection.annotation.discovery.annotation.InitializerMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AppInitializer {

    private static final String APP_PACKAGE = "app";

    public static void main(String[] args) throws Throwable {
        initialize(APP_PACKAGE);
    }

    private static void initialize(String... packageNames) throws Exception {
        List<Class<?>> classes = getAllClasses(packageNames);
        for (Class<?> clazz : classes) {
            if (!clazz.isAnnotationPresent(InitializerClass.class)) {
                continue;
            }
            List<Method> methods = getAllInitializingMethods(clazz);
            // ~ assume class has no-args constructor
            Object instance = clazz.getDeclaredConstructor().newInstance();
            for (Method method : methods) {
                method.invoke(instance);
            }
        }
    }

    private static List<Method> getAllInitializingMethods(Class<?> type) {
        List<Method> initializingMethods = new ArrayList<>();
        for (Method method : type.getDeclaredMethods()) {
            if (method.isAnnotationPresent(InitializerMethod.class)) {
                initializingMethods.add(method);
            }
        }
        return initializingMethods;
    }

    private static List<Class<?>> getAllClasses(String... packageNames) throws URISyntaxException, IOException, ClassNotFoundException {
        List<Class<?>> allClasses = new ArrayList<>();
        for (String packageName : packageNames) {
            String packageRelativePath = packageName.replace('.', '/');
            URI packageUri = AppInitializer.class.getResource(packageRelativePath).toURI();
            if (packageUri.getScheme().equals("file")) {
                Path packageFullPath = Paths.get(packageUri);
                allClasses.addAll(getAllPackageClasses(packageFullPath, packageName));
            } else if (packageUri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(packageUri, Collections.emptyMap());
                Path packageFullPathInJar = fileSystem.getPath(packageRelativePath);
                allClasses.addAll(getAllPackageClasses(packageFullPathInJar, packageName));
                fileSystem.close();
            }
        }
        return allClasses;
    }

    private static List<Class<?>> getAllPackageClasses(Path packagePath, String packageName) throws IOException, ClassNotFoundException {
        if (!Files.exists(packagePath)) {
            return Collections.emptyList();
        }
        List<Path> files = Files.list(packagePath).filter(Files::isRegularFile).toList();
        List<Class<?>> classes = new ArrayList<>();
        for (Path filePath : files) {
            String fileName = filePath.getFileName().toString();
            if (fileName.endsWith(".class")) {
                String classFullName = packageName.isBlank()
                        ? fileName.replaceFirst("\\.class$", "")
                        : packageName + "." + fileName.replaceFirst("\\.class$", "");
                Class<?> type = Class.forName(classFullName);
                classes.add(type);
            }
        }
        return classes;
    }

}
