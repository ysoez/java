package server.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WorkerTaskResult implements Serializable {

    private final Map<String, DocumentStats> docStatsMap = new HashMap<>();

    public void addDocumentStats(String document, DocumentStats documentStats) {
        docStatsMap.put(document, documentStats);
    }

    public Map<String, DocumentStats> documentStatsMap() {
        return Collections.unmodifiableMap(docStatsMap);
    }

    @Override
    public String toString() {
        return "WorkerTaskResult{" +
                "docStatsMap=" + docStatsMap +
                '}';
    }
}
