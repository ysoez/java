package util;

@Deprecated
public @interface Complexity {

    String runtime() default "";

    String space() default "";

}
