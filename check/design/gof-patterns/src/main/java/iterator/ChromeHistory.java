package iterator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

class ChromeHistory {

    @Getter
    private final List<String> urls = new ArrayList<>();

    void push(String url) {
        urls.add(url);
    }

    String pop() {
        var lastIndex = urls.size() - 1;
        var lastUrl = urls.get(lastIndex);
        urls.remove(lastUrl);
        return lastUrl;
    }

    Iterator<String> iterator() {
        return new ListIterator(urls);
    }

    @RequiredArgsConstructor
    static class ListIterator implements Iterator<String> {

        private final List<String> list;
        private int index;

        @Override
        public boolean hasNext() {
            return index < list.size();
        }

        @Override
        public String next() {
            return list.get(index++);
        }
    }

}
