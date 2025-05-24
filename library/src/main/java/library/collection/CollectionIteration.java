package library.collection;

import java.util.Iterator;

class CollectionIteration {

    public static void main(String[] args) {
        var list = new GenericList<String>();
        list.add("hello");
        list.add("world");
        for (String str : list) {
            System.out.println(str);
        }
    }

    private static class GenericList<T> implements Iterable<T> {

        @SuppressWarnings("unchecked")
        private final T[] items = (T[]) new Object[10];
        private int count;

        public void add(T item) {
            items[count++] = item;
        }

        @Override
        public Iterator<T> iterator() {
            return new ListIterator<>(this);
        }

        private static class ListIterator<T> implements Iterator<T> {

            private final GenericList<T> container;
            private int index;

            private ListIterator(GenericList<T> container) {
                this.container = container;
            }

            @Override
            public boolean hasNext() {
                return index < container.count;
            }

            @Override
            public T next() {
                return container.items[index++];
            }
        }
    }
}
