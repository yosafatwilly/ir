package id.ac.usd.ir.payload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Yosafat Willy Christian
 */
public class IRResponse {
    private HashSet<String> vocabulary;
    private HashMap<String,Double> idf;
    private HashMap<String, ArrayList<Integer>> df;
    private int jumlahTerm;
    private double[][] tfidf;

    public IRResponse() {
    }

    public IRResponse(HashSet<String> vocabulary, HashMap<String, Double> idf, HashMap<String, ArrayList<Integer>> df, int jumlahTerm, double[][] tfidf) {
        this.vocabulary = vocabulary;
        this.idf = idf;
        this.df = df;
        this.jumlahTerm = jumlahTerm;
        this.tfidf = tfidf;
    }

    public HashSet<String> getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(HashSet<String> vocabulary) {
        this.vocabulary = vocabulary;
    }

    public HashMap<String, Double> getIdf() {
        return idf;
    }

    public void setIdf(HashMap<String, Double> idf) {
        this.idf = idf;
    }

    public HashMap<String, ArrayList<Integer>> getDf() {
        return df;
    }

    public void setDf(HashMap<String, ArrayList<Integer>> df) {
        this.df = df;
    }

    public int getJumlahTerm() {
        return jumlahTerm;
    }

    public void setJumlahTerm(int jumlahTerm) {
        this.jumlahTerm = jumlahTerm;
    }

    public double[][] getTfidf() {
        return tfidf;
    }

    public void setTfidf(double[][] tfidf) {
        this.tfidf = tfidf;
    }
}
