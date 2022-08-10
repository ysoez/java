package util;

public @interface Complexity {

    String runtime() default "";

    String space() default "";

}
