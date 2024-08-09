package stream.intermediate;

import java.util.stream.Stream;

class StreamMap {

    public static void main(String[] args) {
        var letters = Stream.of('a', 'b', 'c', 'd')
                .map(Character::toUpperCase)
                .toList();
        System.out.println(letters);
    }

}
