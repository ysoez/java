package fe.model;

import java.util.Collections;
import java.util.List;

public class SearchResponse {

    private List<Result> searchResults = Collections.emptyList();
    private String documentsLocation = "";

    public SearchResponse(List<Result> searchResults, String documentsLocation) {
        this.searchResults = searchResults;
        this.documentsLocation = documentsLocation;
    }

    public List<Result> getSearchResults() {
        return searchResults;
    }

    public String getDocumentsLocation() {
        return documentsLocation;
    }

    public static class Result {
        private String title;
        private String extension;
        private int score;

        public Result(String title, String extension, int score) {
            this.title = title;
            this.extension = extension;
            this.score = score;
        }

        public String getTitle() {
            return title;
        }

        public String getExtension() {
            return extension;
        }

        public int getScore() {
            return score;
        }
    }
}
