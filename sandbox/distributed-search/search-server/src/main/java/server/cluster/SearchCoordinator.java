package server.cluster;

import cluster.SerializationUtils;
import cluster.http.client.HttpClient;
import cluster.http.server.HttpMethod;
import cluster.http.server.HttpTransaction;
import cluster.http.server.handler.HttpRequestHandler;
import cluster.model.DocumentSearchRequest;
import cluster.model.DocumentSearchResponse;
import cluster.registry.ServiceRegistry;
import cluster.serialization.Serializer;
import lombok.extern.slf4j.Slf4j;
import server.model.DocumentStats;
import server.model.WorkerTask;
import server.model.WorkerTaskResult;
import server.util.TFIDF;
import server.util.TextParser;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static server.util.FileUtils.BOOKS_DIRECTORY;

@Slf4j
public class SearchCoordinator implements HttpRequestHandler {

    private final ServiceRegistry workersRegistry;
    private final HttpClient client;
    private final Serializer serializer;
    private final List<String> documents;

    public SearchCoordinator(
            ServiceRegistry workersRegistry,
            HttpClient client,
            Serializer serializer) {
        this.workersRegistry = Objects.requireNonNull(workersRegistry);
        this.client = Objects.requireNonNull(client);
        this.serializer = Objects.requireNonNull(serializer);
        this.documents = TextParser.readDocumentPaths(BOOKS_DIRECTORY);
    }

    @Override
    public String endpoint() {
        return "/search";
    }

    @Override
    public EnumSet<HttpMethod> allowedMethods() {
        return EnumSet.of(HttpMethod.POST);
    }

    @Override
    public void handle(HttpTransaction http) throws IOException {
        List<String> workers;
        try {
            workers = workersRegistry.getServices();
        } catch (Exception e) {
            log.error("failed to resolve available workers", e);
            throw new RuntimeException(e); // todo: error handling
        }
        if (workers.isEmpty()) {
            //todo: throw error or empty response?
            http.sendOk(DocumentSearchRequest.getDefaultInstance().toByteArray());
            return;
        }
        try {
            var request = DocumentSearchRequest.parseFrom(http.payload());
            var searchTerms = TextParser.parseWordsFromLine(request.getQuery());
            var workerTasks = getWorkerTasks(workers.size(), searchTerms);
            var workerResults = sendTasksToWorkers(workers, workerTasks);
            var sortedDocuments = aggregateResults(searchTerms, workerResults);;
            var response = DocumentSearchResponse.newBuilder()
                    .addAllRelevantDocuments(sortedDocuments)
                    .build();
            http.sendOk(response.toByteArray());;
        } catch (Exception e) {
            e.printStackTrace();
            http.sendOk(DocumentSearchResponse.getDefaultInstance().toByteArray());;
        }
    }

    private List<DocumentSearchResponse.DocumentStats> aggregateResults(List<String> terms, List<WorkerTaskResult> results) {
        Map<String, DocumentStats> allDocumentsResults = new HashMap<>();
        for (WorkerTaskResult result : results) {
            allDocumentsResults.putAll(result.documentStatsMap());
        }
        Map<Double, List<String>> scoreToDocuments = TFIDF.documentScoreMap(terms, allDocumentsResults);
        return sortDocumentsByScore(scoreToDocuments);
    }

    private List<DocumentSearchResponse.DocumentStats> sortDocumentsByScore(Map<Double, List<String>> scoreToDocuments) {
        List<DocumentSearchResponse.DocumentStats> sortedDocumentsStatsList = new ArrayList<>();
        for (Map.Entry<Double, List<String>> docScorePair : scoreToDocuments.entrySet()) {
            double score = docScorePair.getKey();
            for (String document : docScorePair.getValue()) {
                File documentPath = new File(document);
                var documentStats = DocumentSearchResponse.DocumentStats.newBuilder()
                        .setScore(score)
                        .setName(documentPath.getName())
                        .setSize(documentPath.length())
                        .build();
                sortedDocumentsStatsList.add(documentStats);
            }
        }
        return sortedDocumentsStatsList;
    }

    private List<WorkerTaskResult> sendTasksToWorkers(List<String> workers, List<WorkerTask> tasks) {
        CompletableFuture<WorkerTaskResult>[] futures = new CompletableFuture[workers.size()];
        for (int i = 0; i < workers.size(); i++) {
            String worker = workers.get(i);
            WorkerTask task = tasks.get(i);
            byte[] payload = SerializationUtils.serialize(task);
            futures[i] = client.sendRequest(worker, payload).thenApply(serializer::deserialize);
        }

        List<WorkerTaskResult> results = new ArrayList<>();
        for (CompletableFuture<WorkerTaskResult> future : futures) {
            try {
                WorkerTaskResult result = future.get();
                results.add(result);
            } catch (InterruptedException | ExecutionException e) {
            }
        }

//        System.out.println(String.format("Received %d/%d results", results.size(), tasks.size()));
        return results;
    }

    public List<WorkerTask> getWorkerTasks(int numberOfWorkers, List<String> searchTerms) {
        List<WorkerTask> tasks = new ArrayList<>();
        for (List<String> documentsPerWorker : partitionedDocuments(numberOfWorkers, documents)) {
            var task = new WorkerTask(searchTerms, documentsPerWorker);
            tasks.add(task);
        }
        return tasks;
    }

    private static List<List<String>> partitionedDocuments(int numberOfWorkers, List<String> documents) {
        int docsPerWorker = (documents.size() + numberOfWorkers - 1) / numberOfWorkers;
        List<List<String>> workersDocuments = new ArrayList<>();
        for (int i = 0; i < numberOfWorkers; i++) {
            int firstDocumentIndex = i * docsPerWorker;
            int lastDocumentIndexExclusive = Math.min(firstDocumentIndex + docsPerWorker, documents.size());
            if (firstDocumentIndex >= lastDocumentIndexExclusive) {
                break;
            }
            List<String> currentWorkerDocuments = new ArrayList<>(documents.subList(firstDocumentIndex, lastDocumentIndexExclusive));
            workersDocuments.add(currentWorkerDocuments);
        }
        return workersDocuments;
    }



}
