package server.cluster;

import cluster.http.server.HttpMethod;
import cluster.http.server.HttpTransaction;
import cluster.http.server.handler.HttpRequestHandler;
import cluster.serialization.JavaSerializer;
import lombok.extern.slf4j.Slf4j;
import server.util.TFIDF;
import server.model.WorkerTask;
import server.model.WorkerTaskResult;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

@Slf4j
public class SearchWorker implements HttpRequestHandler, WorkerTaskExecutor {

    private final JavaSerializer serializer;

    public SearchWorker(JavaSerializer serializer) {
        this.serializer = Objects.requireNonNull(serializer);
    }

    @Override
    public String endpoint() {
        return "/task";
    }

    @Override
    public EnumSet<HttpMethod> allowedMethods() {
        return EnumSet.of(HttpMethod.GET);
    }

    @Override
    public void handle(HttpTransaction http) throws IOException {
        WorkerTask task = serializer.deserialize(http.payload());
        WorkerTaskResult result = execute(task);
        byte[] responseBody = serializer.serialize(result);
        http.sendOk(responseBody);
    }

    @Override
    public WorkerTaskResult execute(WorkerTask task) {
        List<String> documents = task.documents();
        log.debug("received {} documents", documents.size());
        var result = new WorkerTaskResult();
        for (String document : documents) {
            var documentStats = TFIDF.createDocumentStats(task.searchTerms(), document);
            result.addDocumentStats(document, documentStats);
        }
        return result;
    }

}
