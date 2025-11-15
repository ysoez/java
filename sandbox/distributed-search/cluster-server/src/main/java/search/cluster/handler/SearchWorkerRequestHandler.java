package search.cluster.handler;

import cluster.SerializationUtils;
import cluster.http.server.HttpTransaction;
import cluster.http.server.sun.AbstractSunHttpRequestHandler;
import search.cluster.model.DocumentData;
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
    public void handle(HttpTransaction transaction) throws IOException {
        var task = (Task) SerializationUtils.deserialize(transaction.requestPayload());
        Result result = createResult(task);
        byte[] responseBody = SerializationUtils.serialize(result);
        transaction.sendOk(responseBody);
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

    @Override
    public String method() {
        return "post";
    }
}
