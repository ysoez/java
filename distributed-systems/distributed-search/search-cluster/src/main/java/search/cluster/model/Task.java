package search.cluster.model;

import java.io.Serializable;
import java.util.List;

public class Task implements Serializable {
    private final List<String> searchTerms;
    private final List<String> documents;

    public Task(List<String> searchTerms, List<String> documents) {
        this.searchTerms = searchTerms;
        this.documents = documents;
    }

    public List<String> getSearchTerms() {
        return searchTerms;
    }

    public List<String> getDocuments() {
        return documents;
    }
}
