package stream.collector;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class RecursiveCollectors {

   @SuppressWarnings("SimplifyStreamApiCallChains")
   public static void main(String[] args) {
       var numbers = List.of(10, 20, 30, 40, 50);

       var min = numbers.stream().collect(Collectors.minBy(Comparator.comparing(e -> e)));
       var max = numbers.stream().collect(Collectors.maxBy(Comparator.comparing(e -> e)));
       System.out.println("\nCollectors.minBy: " + min);
       System.out.println("Collectors.maxBy: " + max);

       min = numbers.stream().min(Comparator.comparing(e -> e));
       max = numbers.stream().max(Comparator.comparing(e -> e));
       System.out.println("\nstream().min: " + min);
       System.out.println("stream().max: " + max);

       MinMax minMax = numbers.stream().collect(Collectors.teeing(
               Collectors.minBy(Comparator.comparing(e -> e)),
               Collectors.maxBy(Comparator.comparing(e -> e)),
               (value1, value2) -> new MinMax(value1.orElse(0), value2.orElse(0))
       ));
       System.out.println("\nCollectors.teeing(): " + minMax);
   }

   record MinMax(Integer min, Integer max) {}


}
