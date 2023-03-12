package util;

public @interface Traversal {

    Type value();

    enum Type {
        PRE_ORDER, IN_ORDER, POST_ORDER, LEVEL_ORDER
    }

}
