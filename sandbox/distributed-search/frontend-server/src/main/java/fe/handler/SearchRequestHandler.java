package fe.handler;

import cluster.http.client.JdkWebClient;
import cluster.http.client.WebClient;
import cluster.http.server.handler.AbstractHttpRequestHandler;
import cluster.model.DocumentSearchRequest;
import cluster.model.DocumentSearchResponse;
import cluster.registry.ServiceRegistry;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.sun.net.httpserver.HttpExchange;
import fe.model.SearchRequest;
import fe.model.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchRequestHandler extends AbstractHttpRequestHandler {

    private static final String DOCUMENTS_LOCATION = "books";
    private final ObjectMapper objectMapper;
    private final WebClient client;
    private final ServiceRegistry searchCoordinatorRegistry;

    public SearchRequestHandler(ServiceRegistry searchCoordinatorRegistry) {
        this.searchCoordinatorRegistry = searchCoordinatorRegistry;
        this.client = new JdkWebClient();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    @Override
    public String endpoint() {
        return "/documents_search";
    }

    @Override
    public String method() {
        return "post";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            SearchRequest frontendSearchRequest = objectMapper.readValue(exchange.getRequestBody().readAllBytes(), fe.model.SearchRequest.class);
            SearchResponse frontendSearchResponse = createFrontendResponse(frontendSearchRequest);
            byte[] responseBody = objectMapper.writeValueAsBytes(frontendSearchResponse);
            sendOk(responseBody, exchange);
        } catch (IOException e) {
            e.printStackTrace();
            sendOk(new byte[0], exchange);
        }
    }

    private fe.model.SearchResponse createFrontendResponse(fe.model.SearchRequest frontendSearchRequest) {
        var searchClusterResponse = sendRequestToSearchCluster(frontendSearchRequest.getSearchQuery());

        List<SearchResponse.Result> filteredResults =
                filterResults(searchClusterResponse,
                        frontendSearchRequest.getMaxNumberOfResults(),
                        frontendSearchRequest.getMinScore());

        return new fe.model.SearchResponse(filteredResults, DOCUMENTS_LOCATION);
    }

    private List<SearchResponse.Result> filterResults(DocumentSearchResponse searchClusterResponse,
                                                      long maxResults,
                                                      double minScore) {

        double maxScore = getMaxScore(searchClusterResponse);

        List<SearchResponse.Result> searchResultInfoList = new ArrayList<>();

        for (int i = 0; i < searchClusterResponse.getRelevantDocumentsCount() && i < maxResults; i++) {

            int normalizedDocumentScore = normalizeScore(searchClusterResponse.getRelevantDocuments(i).getScore(), maxScore);
            if (normalizedDocumentScore < minScore) {
                break;
            }

            String documentName = searchClusterResponse.getRelevantDocuments(i).getName();

            String title = getDocumentTitle(documentName);
            String extension = getDocumentExtension(documentName);

            SearchResponse.Result resultInfo = new SearchResponse.Result(title, extension, normalizedDocumentScore);

            searchResultInfoList.add(resultInfo);
        }

        return searchResultInfoList;
    }



    private static String getDocumentExtension(String document) {
        String[] parts = document.split("\\.");
        if (parts.length == 2) {
            return parts[1];
        }
        return "";
    }

    private static String getDocumentTitle(String document) {
        return document.split("\\.")[0];
    }

    private static int normalizeScore(double inputScore, double maxScore) {
        return (int) Math.ceil(inputScore * 100.0 / maxScore);
    }

    private static double getMaxScore(DocumentSearchResponse searchClusterResponse) {
        if (searchClusterResponse.getRelevantDocumentsCount() == 0) {
            return 0;
        }
        return searchClusterResponse.getRelevantDocumentsList()
                .stream()
                .map(document -> document.getScore())
                .max(Double::compareTo)
                .get();
    }

    private DocumentSearchResponse sendRequestToSearchCluster(String searchQuery) {
        var searchRequest = DocumentSearchRequest.newBuilder()
                .setQuery(searchQuery)
                .build();
        try {
            var coordinatorAddress = searchCoordinatorRegistry.getRandomService();
            if (coordinatorAddress.isEmpty()) {
                System.out.println("Search Cluster Coordinator is unavailable");
                return DocumentSearchResponse.getDefaultInstance();
            }
            byte[] payloadBody = client.sendTask(coordinatorAddress.get(), searchRequest.toByteArray()).join();
            return DocumentSearchResponse.parseFrom(payloadBody);
        } catch (Exception e) {
            e.printStackTrace();
            return DocumentSearchResponse.getDefaultInstance();
        }
    }

}
