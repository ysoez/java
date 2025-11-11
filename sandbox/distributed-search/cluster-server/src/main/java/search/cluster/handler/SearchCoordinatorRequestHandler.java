package search.cluster.handler;

import cluster.SerializationUtils;
import cluster.http.client.WebClient;
import cluster.http.server.HttpTransaction;
import cluster.http.server.handler.AbstractSunHttpRequestHandler;
import cluster.model.DocumentSearchRequest;
import cluster.model.DocumentSearchResponse;
import cluster.registry.ServiceRegistry;
import search.cluster.model.DocumentData;
import search.cluster.model.Result;
import search.cluster.model.Task;
import search.cluster.util.TFIDF;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class SearchCoordinatorRequestHandler extends AbstractSunHttpRequestHandler {

    private static final String BOOKS_DIRECTORY = "./sandbox/distributed-search/books";
    private final ServiceRegistry workersRegistry;
    private final WebClient client;
    private final List<String> documents;

    public SearchCoordinatorRequestHandler(ServiceRegistry workersRegistry, WebClient client) {
        this.workersRegistry = workersRegistry;
        this.client = client;
        this.documents = readDocumentsList();
    }

    @Override
    public void handle(HttpTransaction httpExchange) throws IOException {
        try {
            var request = DocumentSearchRequest.parseFrom(httpExchange.requestPayload());
            var response = createResponse(request);
            httpExchange.sendOk(response.toByteArray());;
        } catch (Exception e) {
            e.printStackTrace();
            httpExchange.sendOk(DocumentSearchResponse.getDefaultInstance().toByteArray());;
        }
    }

    @Override
    public String endpoint() {
        return "/search";
    }

    private DocumentSearchResponse createResponse(DocumentSearchRequest searchRequest) throws Exception {
        var searchResponse = DocumentSearchResponse.newBuilder();
        System.out.println("Received search query: " + searchRequest.getQuery());
        List<String> searchTerms = TFIDF.getWordsFromLine(searchRequest.getQuery());
        List<String> workers = workersRegistry.getServices();

        if (workers.isEmpty()) {
            System.out.println("No search workers currently available");
            return searchResponse.build();
        }

        List<Task> tasks = createTasks(workers.size(), searchTerms);
        List<Result> results = sendTasksToWorkers(workers, tasks);

        List<DocumentSearchResponse.DocumentStats> sortedDocuments = aggregateResults(results, searchTerms);
        searchResponse.addAllRelevantDocuments(sortedDocuments);

        return searchResponse.build();
    }

    private List<DocumentSearchResponse.DocumentStats> aggregateResults(List<Result> results, List<String> terms) {
        Map<String, DocumentData> allDocumentsResults = new HashMap<>();

        for (Result result : results) {
            allDocumentsResults.putAll(result.getDocumentToDocumentData());
        }
        System.out.println("allDocumentsResults: " + allDocumentsResults.values());
        System.out.println("Calculating score for all the documents");
        Map<Double, List<String>> scoreToDocuments = TFIDF.getDocumentsScores(terms, allDocumentsResults);
        System.out.println("scoreToDocuments: " + scoreToDocuments);
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

    private List<Result> sendTasksToWorkers(List<String> workers, List<Task> tasks) {
        CompletableFuture<Result>[] futures = new CompletableFuture[workers.size()];
        for (int i = 0; i < workers.size(); i++) {
            String worker = workers.get(i);
            Task task = tasks.get(i);
            byte[] payload = SerializationUtils.serialize(task);

            futures[i] = client.sendRequest(worker, payload);
        }

        List<Result> results = new ArrayList<>();
        for (CompletableFuture<Result> future : futures) {
            try {
                Result result = future.get();
                results.add(result);
            } catch (InterruptedException | ExecutionException e) {
            }
        }
        System.out.println(String.format("Received %d/%d results", results.size(), tasks.size()));
        return results;
    }

    public List<Task> createTasks(int numberOfWorkers, List<String> searchTerms) {
        List<List<String>> workersDocuments = splitDocumentList(numberOfWorkers, documents);
        List<Task> tasks = new ArrayList<>();
        for (List<String> documentsForWorker : workersDocuments) {
            Task task = new Task(searchTerms, documentsForWorker);
            tasks.add(task);
        }
        return tasks;
    }

    private static List<List<String>> splitDocumentList(int numberOfWorkers, List<String> documents) {
        int numberOfDocumentsPerWorker = (documents.size() + numberOfWorkers - 1) / numberOfWorkers;

        List<List<String>> workersDocuments = new ArrayList<>();

        for (int i = 0; i < numberOfWorkers; i++) {
            int firstDocumentIndex = i * numberOfDocumentsPerWorker;
            int lastDocumentIndexExclusive = Math.min(firstDocumentIndex + numberOfDocumentsPerWorker, documents.size());

            if (firstDocumentIndex >= lastDocumentIndexExclusive) {
                break;
            }
            List<String> currentWorkerDocuments = new ArrayList<>(documents.subList(firstDocumentIndex, lastDocumentIndexExclusive));

            workersDocuments.add(currentWorkerDocuments);
        }
        return workersDocuments;
    }

    private static List<String> readDocumentsList() {
        File documentsDirectory = new File(BOOKS_DIRECTORY);
        return Arrays.asList(documentsDirectory.list())
                .stream()
                .map(documentName -> BOOKS_DIRECTORY + "/" + documentName)
                .collect(Collectors.toList());
    }

    @Override
    public String method() {
        return "get";
    }
}
