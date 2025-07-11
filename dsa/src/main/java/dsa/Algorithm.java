package dsa;

public @interface Algorithm {

    Complexity complexity();

    Target target() default Target.IN_PLACE;

    Traversal traversal() default Traversal.NONE;

    @interface Complexity {
        Runtime runtime();
        Space space();

        enum Runtime {
            CONSTANT,      // O(1)
            LOGARITHMIC,   // O(log n)
            LINEAR,        // O(n)
            LINEARITHMIC,  // O(n log n)
            QUADRATIC,     // O(n^2)
            EXPONENTIAL    // O(2^n);
        }

        enum Space {
            CONSTANT,
            LOGARITHMIC,
            LINEAR,
            QUADRATIC
        }
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
