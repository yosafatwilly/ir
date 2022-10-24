package id.ac.usd.ir.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yosafat Willy Christian
 */
public class Document implements Comparable {

    private int id;
    private String judul;
    private String Kategori;
    private String content;
    private List<String> tokens = null;
    private double[] derajadKeanggotaan;

    public Document() {
    }

    public Document(int id, String judul, String kategori, String content, List<String> tokens, double[] derajadKeanggotaan) {
        this.id = id;
        this.judul = judul;
        Kategori = kategori;
        this.content = content;
        this.tokens = tokens;
        this.derajadKeanggotaan = derajadKeanggotaan;
    }

    public String getKategori() {
        return Kategori;
    }

    public void setKategori(String kategori) {
        Kategori = kategori;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

//    @JsonIgnore
    public String getFullContent() {
        return content;
    }

    public String getContent() {
        return (this.content.length() > 250? this.content.substring(0, 250)+"...":this.content);
    }

    public void setContent(String content) {
        this.content = content;
    }

//    @JsonIgnore
    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    @JsonIgnore
    public double[] getDerajadKeanggotaan() {
        return derajadKeanggotaan;
    }

    @JsonIgnore
    public String getDerajadKeanggotaanAsString() {
        return Arrays.toString(derajadKeanggotaan);
    }

    public void setDerajadKeanggotaan(double[] derajadKeanggotaan) {
        this.derajadKeanggotaan = derajadKeanggotaan;
    }

    @Override
    public int compareTo(Object arg0) {
        Document other = (Document)arg0;
        if(this.id == other.id && this.content != null && other.content != null) return this.content.compareTo(other.content);
        return this.id - other.id;
    }

    @Override
    public String toString() {
        return "[ID:" + this.id + ", " + (this.content.length() > 50 ? this.content.substring(0, 50) + "..." : this.content) + "]";
    }
}
