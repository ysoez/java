package search.cluster.handler;

import cluster.network.http.AbstractHttpRequestHandler;
import search.cluster.model.DocumentData;
import search.cluster.model.Result;
import cluster.network.SerializationUtils;
import search.cluster.model.Task;
import search.cluster.util.TFIDF;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SearchWorkerRequestHandler extends AbstractHttpRequestHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var task = (Task) SerializationUtils.deserialize(exchange.getRequestBody().readAllBytes());
        Result result = createResult(task);
        byte[] responseBody = SerializationUtils.serialize(result);
        sendOk(responseBody, exchange);
    }

    @Override
    public String endpoint() {
        return "/task";
    }

    private Result createResult(Task task) {
        List<String> documents = task.getDocuments();
        System.out.println(String.format("Received %d documents to process", documents.size()));
        Result result = new Result();

        for (String document : documents) {
            List<String> words = parseWordsFromDocument(document);
            DocumentData documentData = TFIDF.createDocumentData(words, task.getSearchTerms());
            System.out.println(document + " : " + documentData);
            result.addDocumentData(document, documentData);
        }
        return result;
    }

    private List<String> parseWordsFromDocument(String document) {
        FileReader fileReader;
        try {
            fileReader = new FileReader(document);
        } catch (FileNotFoundException e) {
            return Collections.emptyList();
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        List<String> words = TFIDF.getWordsFromDocument(lines);
        return words;
    }

}
