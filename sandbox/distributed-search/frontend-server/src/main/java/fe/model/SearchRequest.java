package fe.model;

import lombok.Getter;

@Getter
public class SearchRequest {
    private String searchQuery;
    private long maxNumberOfResults = Long.MAX_VALUE;
    private double minScore = 0.0;
}
