package search.cluster.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable {

    private Map<String, DocumentStats> documentToDocumentData = new HashMap<>();

    public void addDocumentData(String document, DocumentStats documentStats) {
        documentToDocumentData.put(document, documentStats);
    }

    public Map<String, DocumentStats> getDocumentToDocumentData() {
        return Collections.unmodifiableMap(documentToDocumentData);
    }

}
