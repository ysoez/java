package fe.handler;

import api.search.DistributedSearchRequest;
import api.search.DistributedSearchResponse;
import api.search.DocumentStats;
import cluster.http.client.HttpClient;
import cluster.http.server.HttpMethod;
import cluster.http.server.HttpTransaction;
import cluster.http.server.handler.HttpRequestHandler;
import cluster.registry.ServiceRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import fe.model.SearchRequest;
import fe.model.SearchResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

@Slf4j
public class SearchRequestHandler implements HttpRequestHandler {

    private static final String DOCUMENTS_LOCATION = "books";
    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private final ServiceRegistry searchCoordinatorRegistry;

    public SearchRequestHandler(ServiceRegistry coordinatorRegistry, HttpClient client, ObjectMapper objectMapper) {
        this.searchCoordinatorRegistry = Objects.requireNonNull(coordinatorRegistry);
        this.client = Objects.requireNonNull(client);
        this.objectMapper = Objects.requireNonNull(objectMapper);
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
        try {
            SearchRequest request = objectMapper.readValue(http.payload(), SearchRequest.class);
            DistributedSearchResponse clusterResponse = sendClusterRequest(request.getSearchQuery());
            long maxResults = request.getMaxNumberOfResults();
            List<SearchResponse.Result> filteredResults = filterResults(clusterResponse, maxResults, request.getMinScore());
            SearchResponse response = new SearchResponse(filteredResults, DOCUMENTS_LOCATION);
            byte[] responseBody = objectMapper.writeValueAsBytes(response);
            http.sendOk(responseBody);
        } catch (Exception e) {
            http.sendError(500, "search failed".getBytes(StandardCharsets.UTF_8));
        }
    }

    private List<SearchResponse.Result> filterResults(
            DistributedSearchResponse response,
            long maxResults,
            double minScore) {
        double maxScore = getMaxScore(response);
        List<SearchResponse.Result> searchResultInfoList = new ArrayList<>();
        for (int i = 0; i < response.getRelevantDocumentsCount() && i < maxResults; i++) {
            int normalizedScore = normalizeScore(response.getRelevantDocuments(i).getScore(), maxScore);
            if (normalizedScore < minScore) {
                break;
            }

            String documentName = response.getRelevantDocuments(i).getName();
            String title = getDocumentTitle(documentName);
            String extension = getDocumentExtension(documentName);

            var resultInfo = new SearchResponse.Result(title, extension, normalizedScore);
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

    private static double getMaxScore(DistributedSearchResponse response) {
        if (response.getRelevantDocumentsCount() == 0) {
            return 0;
        }
        return response.getRelevantDocumentsList()
                .stream()
                .map(DocumentStats::getScore)
                .max(Double::compareTo)
                .get();
    }

    private DistributedSearchResponse sendClusterRequest(String searchQuery) {
        var request = DistributedSearchRequest.newBuilder()
                .setQuery(searchQuery)
                .build();
        try {
            var coordinatorAddress = searchCoordinatorRegistry.getRandomService();
            if (coordinatorAddress.isEmpty()) {
                //todo: fail or empty response?
                System.out.println("Search Cluster Coordinator is unavailable");
                return DistributedSearchResponse.getDefaultInstance();
            }
            byte[] payload = client.sendRequest(coordinatorAddress.get(), request.toByteArray()).join();
            return DistributedSearchResponse.parseFrom(payload);
        } catch (Exception e) {
            e.printStackTrace();
            //todo: fail or empty response?
            return DistributedSearchResponse.getDefaultInstance();
        }
    }

}
