package library.reflection.field;

import java.lang.reflect.Field;

class FieldInspector {

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        printDeclaredFields(Movie.class);
        printDeclaredFields(Movie.Stats.class);
        printDeclaredFields(Category.class);
        printDeclaredFields(Movie.class, new Movie("Lord of the Rings", 2001, 12.99, true, Category.ADVENTURE));

        Field staticField = Movie.class.getDeclaredField("MINIMUM_PRICE");
        System.out.printf("Static MINIMUM_PRICE value :%f%n", staticField.get(null));
    }

    static void printDeclaredFields(Class<?> type) {
        System.out.printf("========= %s =========\n", type.getSimpleName());
        for (Field field : type.getDeclaredFields()) {
            System.out.printf("Field name : %s%n", field.getName());
            System.out.printf("Field type: %s%n", field.getType().getName());
            System.out.printf("Is synthetic: %s%n", field.isSynthetic());
            System.out.printf("Access flags: %s%n\n", field.accessFlags());
        }
    }

    static <T> void printDeclaredFields(Class<? extends T> type, T instance) throws IllegalAccessException {
        System.out.printf("========= %s =========\n", type.getSimpleName());
        for (Field field : type.getDeclaredFields()) {
            System.out.printf("Field name: %s%n", field.getName());
            System.out.printf("Field type: %s%n", field.getType().getName());
            System.out.printf("Field value: %s%n\n", field.get(instance));
        }
    }

    enum Category {
        ADVENTURE,
        ACTION,
        COMEDY
    }

    static class Product {
        protected String name;
        protected int year;
        protected double actualPrice;

        public Product(String name, int year) {
            this.name = name;
            this.year = year;
        }
    }

    static class Movie extends Product {
        public static final double MINIMUM_PRICE = 10.99;

        private boolean isReleased;
        private Category category;
        private double actualPrice;

        public Movie(String name, int year, double price, boolean isReleased, Category category) {
            super(name, year);
            this.isReleased = isReleased;
            this.category = category;
            this.actualPrice = Math.max(price, MINIMUM_PRICE);
        }

        class Stats {
            private final double timesWatched;

            Stats(double timesWatched) {
                this.timesWatched = timesWatched;
            }

            double getRevenue() {
                return timesWatched * actualPrice;
            }
        }
    }

}
