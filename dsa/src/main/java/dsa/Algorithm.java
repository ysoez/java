package dsa;

public @interface Algorithm {

    Complexity complexity();

    Assumption[] assumptions() default {};

    Target target() default Target.IN_PLACE;

    Traversal traversal() default Traversal.NONE;

    @interface Complexity {
        String runtime();
        String space();
        String CONSTANT = "O(1)";
        String LOGARITHMIC = "O(log n)";
        String LINEAR = "O(n)";
        String LINEARITHMIC = "O(n * log n)";
        String QUADRATIC = "O(n^2)";
        String EXPONENTIAL = " O(2^n);";
    }

    enum Assumption {
        NONE,
        ORDERING,
        UNIQUENESS
    }

    enum Target {
        IN_PLACE,
        OUT_OF_PLACE
    }

    enum Traversal {
        NONE,
        LEVEL_ORDER,
        PRE_ORDER,
        IN_ORDER,
        POST_ORDER
    }

}
