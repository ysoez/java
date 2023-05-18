package util;

public @interface Algorithm {

    Complexity complexity();

    Implementation[] implementation() default Implementation.SEQUENTIAL;

    Target target() default Target.IN_PLACE;

    @interface Complexity {
        Value runtime();

        Value space();

        enum Value {
            CONSTANT,
            LOGARITHMIC,
            LINEAR,
            N_LOG_N,
            POLYNOMIAL,
            EXPONENTIAL
        }

    }

    enum Implementation {
        RECURSIVE,
        PARALLEL_OR_DISTRIBUTED,
        SEQUENTIAL
    }

    enum Target {
        IN_PLACE, OUT_OF_PLACE
    }

}
