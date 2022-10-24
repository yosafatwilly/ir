package id.ac.usd.ir.payload.evaluator;

import java.util.List;
import java.util.TreeMap;

public class Evaluator {
    private String query;
    private TreeMap<Double, Double> interpolation;
    private List<RecallPrecision> recallPrecision;
    private long time;
    private Integer relevantCount;

    public Evaluator() {
    }

    public Evaluator(String query, TreeMap<Double, Double> interpolation, List<RecallPrecision> recallPrecision) {
        this.query = query;
        this.interpolation = interpolation;
        this.recallPrecision = recallPrecision;
    }

    public List<RecallPrecision> getRecallPrecision() {
        return recallPrecision;
    }


    public Integer getRelevantCount() {
        return relevantCount;
    }

    public void setRelevantCount(Integer relevantCount) {
        this.relevantCount = relevantCount;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setRecallPrecision(List<RecallPrecision> recallPrecision) {
        this.recallPrecision = recallPrecision;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public TreeMap<Double, Double> getInterpolation() {
        return interpolation;
    }

    public void setInterpolation(TreeMap<Double, Double> interpolation) {
        this.interpolation = interpolation;
    }
}

