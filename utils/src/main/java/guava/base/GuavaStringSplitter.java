package guava.base;

import com.google.common.base.Splitter;

class GuavaStringSplitter {

    public static void main(String[] args) {
        Iterable<String> output = Splitter.on(",")
                .trimResults()
                .omitEmptyStrings()
                .split(" foo, ,bar, quu, ");
        System.out.println(output);
    }

}