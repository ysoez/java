package iterator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

class SafariHistory {

    @Getter
    private final String[] urls = new String[10];
    private int count;

    void push(String url) {
        urls[count++] = url;
    }

    String pop() {
        return urls[--count];
    }

    Iterator<String> iterator() {
        return new ArrayIterator(urls);
    }

    @RequiredArgsConstructor
    class ArrayIterator implements Iterator<String> {

        private final String[] array;
        private int index;

        @Override
        public boolean hasNext() {
            return index < count;
        }

        @Override
        public String next() {
            return array[index++];
        }
    }

}
