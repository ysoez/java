package vavr.value;

import io.vavr.control.Try;

class VavrTry {

    @SuppressWarnings({"NumericOverflow", "divzero", "PointlessArithmeticExpression"})
    public static void main(String[] args) {
        //
        // ~ success path
        //
        Try.of(() -> 10 / 2).map(num -> num / 5).andThen(v -> System.out.println(v));
        //
        // ~ failure path
        //
        var failure = Try.of(() -> 10 / 0).onFailure(System.err::println);
        ;
        //
        // ~ safe fallback
        //
        System.out.println("fallback: " + failure.getOrElse(-1));
        //
        // ~ recover
        //
        Try<Integer> recovered = failure.recover(ArithmeticException.class, _ -> 0);
        System.out.println("fallback on ArithmeticException: " + recovered);
        //
        // ~ recover with
        //
        var recoverWith = failure.recoverWith(ArithmeticException.class, _ -> Try.of(() -> 10 / 1));
        System.out.println("recoverWith → " + recoverWith);
    }

}