package apache.collection;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

class ApachePredicatedCollection {

    public static void main(String[] args) {
        Collection<String> safeCollection = CollectionUtils.predicatedCollection(new ArrayList<>(), Objects::nonNull);
        safeCollection.add("test");
        safeCollection.add(null);
    }

}
