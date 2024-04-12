package type;

import java.util.List;

class SmartTypeInference {

    public static void main(String[] args) {
        var list = List.of(1, 2, "a", new StringBuilder("b"));
        class Local {}
        // ~ T -> Serializable & Comparable<? extends Serializable & Comparable<?>>
        // list.add(new Local());
    }

}
