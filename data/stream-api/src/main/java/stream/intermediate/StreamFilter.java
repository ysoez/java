package stream.intermediate;

import java.util.stream.Stream;

class StreamFilter {

    public static void main(String[] args) {
        var digits = Stream.of('a', '1', 'b', '2', 'c', '3')
                .filter(Character::isDigit)
                .toList();
        System.out.println(digits);
    }

}
