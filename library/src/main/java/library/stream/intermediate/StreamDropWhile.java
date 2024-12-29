package library.stream.intermediate;

import java.time.LocalDate;
import java.util.stream.Stream;

class StreamDropWhile {

    public static void main(String[] args) {
        Stream.of(
                LocalDate.ofYearDay(1999, 1),
                LocalDate.ofYearDay(2001, 1),
                LocalDate.ofYearDay(2002, 1),
                LocalDate.ofYearDay(2000, 1)
        ).dropWhile(date -> date.getYear() <= 2000).forEach(System.out::println);
    }

}
