package library.stream.intermediate;

import java.util.stream.Stream;

class StreamDistinct {

    public static void main(String[] args) {
        var uniqueDigits = Stream.of(1, 2, 1, 2, 3, 4, 4, 3, 2, 1)
                .distinct()
                .toList();
        System.out.println(uniqueDigits);
    }

}
