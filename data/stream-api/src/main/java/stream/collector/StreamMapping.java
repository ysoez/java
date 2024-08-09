package stream.collector;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

class StreamMapping {

    public static void main(String[] args) {
        List<Language> employees = List.of(
                new Language("Assembly", Type.NATIVE),
                new Language("C", Type.NATIVE),
                new Language("C++", Type.NATIVE),
                new Language("Java", Type.MANAGED),
                new Language("C#", Type.MANAGED)
        );
        System.out.println(languagesByType(employees));
    }

    private static Map<Type, List<String>> languagesByType(List<Language> languages) {
        return languages.stream().collect(groupingBy(Language::type, mapping(Language::name, toList())));
    }

    private record Language(String name, Type type) {
    }

    private enum Type {
        NATIVE, MANAGED
    }

}
