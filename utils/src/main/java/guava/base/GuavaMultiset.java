package guava.base;

import com.google.common.collect.ImmutableMultiset;

class GuavaMultiset {

    public static void main(String[] args) {
        //
        // ~ ImmutableMultiset is similar to Map<E, Integer>
        //
        ImmutableMultiset<String> bag = ImmutableMultiset.of("A", "B", "B", "C", "C", "C");
        System.out.println(bag);
        System.out.println("size=" + bag.size());
        System.out.println("Unique elements:");
        for (String s : bag.elementSet()) {
            System.out.print(s);
        }
    }

}