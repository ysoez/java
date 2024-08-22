package library.reflection;

import java.util.*;

class ClassInspector {

    private static final List<String> JDK_PACKAGE_PREFIXES = List.of("com.sun.", "java", "javax", "jdk", "org.w3c", "org.xml");

    public static void main(String[] args) {
        System.out.println(createPopupTypeInfoFromClass(Map.class));
        System.out.println(createPopupTypeInfoFromClass(List.class));
    }


    private static TypeInfo createPopupTypeInfoFromClass(Class<?> type) {
        return new TypeInfo()
                .setPrimitive(type.isPrimitive())
                .setInterface(type.isInterface())
                .setEnum(type.isEnum())
                .setName(type.getSimpleName())
                .setJdk(isJdkClass(type))
                .addAllInheritedClassNames(getAllInheritedClassNames(type));
    }

    private static boolean isJdkClass(Class<?> type) {
        return JDK_PACKAGE_PREFIXES.stream().anyMatch(packagePrefix -> type.getPackage() == null
                || type.getPackage().getName().startsWith(packagePrefix));
    }

    private static String[] getAllInheritedClassNames(Class<?> type) {
        String[] inheritedClasses;
        if (type.isInterface()) {
            inheritedClasses = Arrays.stream(type.getInterfaces())
                    .map(Class::getSimpleName)
                    .toArray(String[]::new);
        } else {
            Class<?> inheritedClass = type.getSuperclass();
            inheritedClasses = inheritedClass != null ? new String[]{type.getSuperclass().getSimpleName()} : null;
        }
        return inheritedClasses;
    }

    private static class TypeInfo {

        private final List<String> inheritedClassNames = new ArrayList<>();
        private boolean isPrimitive;
        private boolean isInterface;
        private boolean isEnum;
        private String name;
        private boolean isJdk;

        public TypeInfo setPrimitive(boolean isPrimitive) {
            this.isPrimitive = isPrimitive;
            return this;
        }

        public TypeInfo setInterface(boolean isInterface) {
            this.isInterface = isInterface;
            return this;
        }

        public TypeInfo setEnum(boolean isEnum) {
            this.isEnum = isEnum;
            return this;
        }

        public TypeInfo setName(String name) {
            this.name = name;
            return this;
        }

        public TypeInfo setJdk(boolean isJdkType) {
            this.isJdk = isJdkType;
            return this;
        }

        public TypeInfo addAllInheritedClassNames(String[] inheritedClassNames) {
            if (inheritedClassNames != null) {
                this.inheritedClassNames.addAll(Arrays.stream(inheritedClassNames).toList());
            }
            return this;
        }

        public List<String> getInheritedClassNames() {
            return Collections.unmodifiableList(inheritedClassNames);
        }

        @Override
        public String toString() {
            return "PopupTypeInfo{" +
                    "isPrimitive=" + isPrimitive +
                    ", isInterface=" + isInterface +
                    ", isEnum=" + isEnum +
                    ", name='" + name + '\'' +
                    ", isJdk=" + isJdk +
                    ", inheritedClassNames=" + inheritedClassNames +
                    '}';
        }
    }

}
