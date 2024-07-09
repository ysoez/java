package reflection.annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class AnnotationInspector {

    public static void main(String[] args) {
        // ~ the @Override has SOURCE retention
        System.out.println(Annotations.getAllAnnotatedMethod(ArrayList.class, Override.class));
        System.out.println(Annotations.getAllAnnotatedMethod(Collections.class, SafeVarargs.class));
        System.out.println(Annotations.getAllAnnotatedMethod(Arrays.class, SafeVarargs.class));
    }

}
