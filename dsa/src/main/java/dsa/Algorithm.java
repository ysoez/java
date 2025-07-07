package dsa;

public @interface Algorithm {

    Complexity complexity();

    Target target() default Target.IN_PLACE;

    Traversal traversal() default Traversal.NONE;

    @interface Complexity {
        Value runtime();
        Value space();
        enum Value {
            CONSTANT,
            HEIGHT_DEPENDENT,
            LOGARITHMIC,
            LINEAR,
            N_LOG_N,
            QUADRATIC,
            EXPONENTIAL
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
