package dsa;

public @interface Algorithm {

    Complexity complexity();

    Assumption[] assumptions() default {};

    Target target() default Target.IN_PLACE;

    Traversal traversal() default Traversal.NONE;

    @interface Complexity {
        Value runtime();
        Value space();
        enum Value {
            CONSTANT,      // O(1)
            LOGARITHMIC,   // O(log n)
            LINEAR,        // O(n)
            LINEARITHMIC,  // O(n log n)
            QUADRATIC,     // O(n^2)
            EXPONENTIAL    // O(2^n);
        }
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
