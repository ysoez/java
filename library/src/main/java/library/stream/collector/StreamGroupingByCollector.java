package library.stream.collector;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

class StreamGroupingByCollector {

    public static void main(String[] args) {
        System.out.println(groupBy(List.of("John", "Sarah", "Mark", "Sarah", "Eric")));
        System.out.println(groupByAndCounting(List.of("John", "Sarah", "Mark", "Sarah", "Eric")));
        System.out.println(groupByAndAverage(List.of(
            new Employee("rnd", 6000d),
            new Employee("rnd", 6000d),
            new Employee("marketing", 1000d),
            new Employee("marketing", 2000d))
        ));
    }

    private static Map<String, List<String>> groupBy(List<String> names) {
        return names.stream().collect(groupingBy(identity()));
    }

    private static Map<String, Long> groupByAndCounting(List<String> names) {
        return names.stream().collect(groupingBy(identity(), counting()));
    }

    private static Map<String, Double> groupByAndAverage(List<Employee> employees) {
        return employees.stream().collect(groupingBy(Employee::department, averagingDouble(Employee::salary)));
    }

    record Employee(String department, Double salary) {
    }

}
