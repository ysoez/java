package stream.source;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class StreamArraySource {

    public static void main(String[] args) {
        int[] numbers = new int[]{1, 2, 3};
        Stream<int[]> arrayStream = Stream.of(numbers);
        System.out.println(arrayStream);
        IntStream intStream = Arrays.stream(numbers);
        System.out.println(intStream);
    }

}
