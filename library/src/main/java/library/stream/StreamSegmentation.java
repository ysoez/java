package library.stream;

import java.util.List;
import java.util.stream.Stream;

class StreamSegmentation {

    public static void main(String[] args) {
        System.out.println("\nstream() => parallel()");
        Stream.of(1, 2, 3)
                .map(num -> logThread("map", num))
                .parallel()
                .forEach(num -> logThread("forEach", num));
        System.out.println("\nparallelStream() => sequential()");
        List.of(1, 2, 3)
                .parallelStream()
                .map(num -> logThread("map", num))
                .sequential()
                .forEach(num -> logThread("forEach", num));
    }

    private static int logThread(String op, int num) {
        System.out.println(op + "(" + num + ")" + " in thread " + Thread.currentThread().getName());
        return num;
    }

}
