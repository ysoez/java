package stream.collector;

import java.util.List;

import static java.util.stream.Collectors.reducing;

class StreamReduceCollector {

    public static void main(String[] args) {
        System.out.println(sum(List.of(1, 2, 3, 4, 5)));
        System.out.println(sumReduceMethod(List.of(1, 2, 3, 4, 5)));
    }

    @SuppressWarnings("SimplifyStreamApiCallChains")
    private static int sum(List<Integer> numbers) {
        return numbers.stream().collect(reducing(0, Integer::sum));
    }

    private static int sumReduceMethod(List<Integer> numbers) {
        return numbers.stream().reduce(0, Integer::sum);
    }

}
