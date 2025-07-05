package dsa;

public @interface Algorithm {

    Complexity complexity();

    Target target() default Target.IN_PLACE;

    @interface Complexity {
        Value runtime();

        Value space();

        enum Value {
            CONSTANT,
            LOGARITHMIC,
            LINEAR,
            N_LOG_N,
            QUADRATIC,
            EXPONENTIAL
        }
    }

    enum Target {
        IN_PLACE, OUT_OF_PLACE
    }

}
