package vavr.tuple;

import io.vavr.Tuple;

class VavrTuple {

    public static void main(String[] args) {
        var tuple = Tuple.of(1, "One");
        System.out.println(tuple._1);
        System.out.println(tuple._2);
    }

}
