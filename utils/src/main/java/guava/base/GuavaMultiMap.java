package guava.base;

import com.google.common.collect.ImmutableMultimap;

class GuavaMultiMap {

    public static void main(String[] args) {
        ImmutableMultimap<String, String> map = ImmutableMultimap.of("A", "1", "A", "2");
        System.out.println(map);
        System.out.println("get(B)=" + map.get("B"));
        System.out.println(map.entries());
        System.out.println(map.size());
    }

}