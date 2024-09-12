package statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

class InstanceOfOperator {

    public static void main(String[] args) {
        patternMatching(new ArrayList<>());
    }

    private static void patternMatching(List<?> list) {
        if (list instanceof Vector<?> v) {
            v.capacity();
        } else if (list instanceof ArrayList<?> al) {
            al.clear();
        }
    }

}
