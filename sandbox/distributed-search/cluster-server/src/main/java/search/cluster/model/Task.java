package search.cluster.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public record Task(List<String> searchTerms, List<String> documents) implements Serializable {

    @Override
    public List<String> searchTerms() {
        return Collections.unmodifiableList(searchTerms);
    }

    @Override
    public List<String> documents() {
        return Collections.unmodifiableList(documents);
    }

}
