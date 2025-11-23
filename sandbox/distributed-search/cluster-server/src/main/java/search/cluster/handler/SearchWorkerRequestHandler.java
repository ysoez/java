package search.cluster.handler;

import cluster.SerializationUtils;
import cluster.http.server.HttpTransaction;
import cluster.http.server.sun.AbstractSunHttpRequestHandler;
import search.cluster.model.DocumentStats;
import search.cluster.model.Result;
import search.cluster.model.Task;
import search.cluster.util.TFIDF;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SearchWorkerRequestHandler extends AbstractSunHttpRequestHandler {

    @Override
    public String endpoint() {
        return "/task";
    }

    @Override
    public String method() {
        return "post";
    }

    @Override
    public void handle(HttpTransaction transaction) throws IOException {
        var task = (Task) SerializationUtils.deserialize(transaction.requestPayload());
        Result result = createResultFor(task);
        byte[] responseBody = SerializationUtils.serialize(result);
        transaction.sendOk(responseBody);
    }

    private Result createResultFor(Task task) {
        List<String> documents = task.documents();
        System.out.printf("received %d documents\n", documents.size());
        var result = new Result();
        for (String document : documents) {
            var documentStats = computeTermFrequency(task, document);
            result.addDocumentStats(document, documentStats);
        }
        return result;
    }

    private DocumentStats computeTermFrequency(Task task, String document) {
        List<String> words = parseDocumentWords(document);
        return TFIDF.createDocumentStats(words, task.searchTerms());
    }

    private List<String> parseDocumentWords(String document) {
        FileReader fileReader;
        try {
            fileReader = new FileReader(document);
        } catch (FileNotFoundException e) {
            return Collections.emptyList();
        }
        var bufferedReader = new BufferedReader(fileReader);
        var lines = bufferedReader.lines().collect(Collectors.toList());
        return TFIDF.getWordsFromDocument(lines);
    }

}
