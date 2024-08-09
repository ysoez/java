package stream.collector;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

class StreamToMap {

    public static void main(String[] args) {
        System.out.println(lengthByName(List.of("Java", "C++", "Assembly")));
    }

    private static Map<String, Integer> lengthByName(List<String> names) {
        return names.stream().collect(toMap(identity(), String::length));
    }

}
