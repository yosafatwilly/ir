package id.ac.usd.ir.payload;

import java.util.List;

public class CustomSearchResponse {

    private List<SearchResponse> results;

    private long timeExecute;

    public CustomSearchResponse() {
    }

    public CustomSearchResponse(List<SearchResponse> results, long timeExecute) {
        this.results = results;
        this.timeExecute = timeExecute;
    }

    public List<SearchResponse> getResults() {
        return results;
    }

    public void setResults(List<SearchResponse> results) {
        this.results = results;
    }

    public long getTimeExecute() {
        return timeExecute;
    }

    public void setTimeExecute(long timeExecute) {
        this.timeExecute = timeExecute;
    }
}
