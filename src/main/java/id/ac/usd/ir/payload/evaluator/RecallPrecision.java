package id.ac.usd.ir.payload.evaluator;

import id.ac.usd.ir.entity.Document;

public class RecallPrecision {
    private Document document;
    private boolean relevan;
    private Double recall;
    private Double precision;

    public RecallPrecision() {
    }

    public RecallPrecision(Document document, boolean relevan, Double recall, Double precision) {
        this.document = document;
        this.relevan = relevan;
        this.recall = recall;
        this.precision = precision;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public boolean isRelevan() {
        return relevan;
    }

    public void setRelevan(boolean relevan) {
        this.relevan = relevan;
    }

    public Double getRecall() {
        return recall;
    }

    public void setRecall(Double recall) {
        this.recall = recall;
    }

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }
}
