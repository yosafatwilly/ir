package id.ac.usd.ir.payload.evaluator;

import id.ac.usd.ir.entity.Document;

import java.util.List;
import java.util.Set;

public class QueryRelevance {
    private String query; //query
    private List<Document> documents; //dokumen relevant
    private Set<Integer> answers; //id dokumen relevant

    public QueryRelevance() {
    }

    public QueryRelevance(String query, Set<Integer> answers) {
        this.query = query;
        this.answers = answers;
    }

    public QueryRelevance(String query, List<Document> documents, Set<Integer> answers) {
        this.query = query;
        this.documents = documents;
        this.answers = answers;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Set<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Integer> answers) {
        this.answers = answers;
    }
}
