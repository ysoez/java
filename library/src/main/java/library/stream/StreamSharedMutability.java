package library.stream;

import java.util.ArrayList;
import java.util.List;

class StreamSharedMutability {

    public static void main(String[] args) {
        modifyOutsidePipeline();
        externalMutabilityAndParallelStream();
    }

    private static void modifyOutsidePipeline() {
        var numbers = List.of(1, 2, 3);
        var factor = new int[]{2};
        var stream = numbers.stream().map(num -> num & factor[0]);
        factor[0] = 0;
        stream.forEach(System.out::print);
    }

    private static void externalMutabilityAndParallelStream() {
        System.out.println();
        while (true) {
            List<String> list = List.of("a", "b", "c", "d", "e", "f", "g");
            List<String> upper = new ArrayList<>();
            list.parallelStream()
                    .map(String::toUpperCase)
                    .forEach(upper::add); // ~ shared mutability
            if (upper.size() != list.size()) {
                throw new RuntimeException();
            }
        }
    }

}
