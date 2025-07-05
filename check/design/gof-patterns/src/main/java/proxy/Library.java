package proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class Library {

    private Map<String, Ebook> shelve = new HashMap<>();

    void add(Ebook realEbook) {
        shelve.put(realEbook.fileName(), realEbook);
    }

    void open(String fileName) {
        Optional.ofNullable(shelve.get(fileName)).ifPresent(Ebook::show);
    }

}
