package guava.base;

import com.google.common.base.Joiner;

class GuavaStringJoiner {

    private static final Joiner JOINER = Joiner.on(", ").skipNulls();

    public static void main(String[] args) {
        System.out.println(JOINER.join("A", "B", null, "C"));
    }

}