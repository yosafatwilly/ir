package id.ac.usd.ir.service;

import id.ac.usd.ir.entity.Document;
import id.ac.usd.ir.payload.SearchResponse;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Yosafat Willy Christian
 */
@Service
public class TFIDFSearcher extends Searcher{

    public HashSet<String> vocabulary; //menyimpan term
    public HashMap<String, Double> idf;  //inverse document frequency
    public HashMap<String, ArrayList<Integer>> df; //document frequency
    public List<Document> docSet; //document list

    public List<String> query; //menampung hasil tokenisasi dari input query user
    public TreeMap<String, Double> dfQ; //document frequency query
    public HashMap<String, Double> qVec; //menyimpan bobot(tf-idf) input query
    public List<SearchResponse> listResult; //menyimpan hasil pencarian

    public TFIDFSearcher() {
        docSet = new ArrayList<>();
    }

    /**
     *
     * @param docSet : Koleksi dokumen yang akan dihitung bobotnya
     */
    public void prepareTfidf(List<Document> docSet) {
        this.docSet = docSet;
        vocabulary = new HashSet<>();
        idf = new HashMap<>();
        df = new HashMap<>();
        /**
         * menghitung df (document frequency) pada
         * seluruh document
         */
        for (Document doc : docSet) {
            ArrayList<String> tempTerm = new ArrayList<>();
            for (String token : doc.getTokens()) {
                vocabulary.add(token);
                if (!df.containsKey(token)) {
                    ArrayList<Integer> docList = new ArrayList<>();
                    docList.add(doc.getId());
                    df.put(token, docList);
                    tempTerm.add(token);
                } else {
                    if (!tempTerm.contains(token)) {
                        df.get(token).add(doc.getId());
                        tempTerm.add(token);
                    }
                }
            }
        }
        /**
         * menghitung idf (inverse document frequency)
         */
        for (Map.Entry<String, ArrayList<Integer>> entry : df.entrySet()) {
            double idfScore = 0.0;
            double dSize = docSet.size();
            double DF = entry.getValue().size();
            idfScore = Math.log10(1.0 + (dSize / DF));
            idf.put(entry.getKey(), idfScore);
        }
    }

    /**
     *
     * @param queryString : input pengguna
     * @return : hasil pencarian
     */
    public List<SearchResponse> search(String queryString) {
        query = tokenizer.tokenize(queryString);
        dfQ = new TreeMap<>();
        qVec = new HashMap<>();
        listResult = new ArrayList<>();
        /**
         * menghitung df (document frequency) query
         */
        for (String str : query) {
            double qfreq = 0.0;
            vocabulary.add(str);
            for (Document d : docSet) {
                if (d.getTokens().contains(str))
                    qfreq++;
            }
            dfQ.put(str, qfreq);
        }
        /**
         * menghitung idf (inverse document frequency) query
         */
        for (Map.Entry<String, Double> entry : dfQ.entrySet()) {
            double idfScore = 0.0;
            if (entry.getValue() != 0)
                idfScore = Math.log10(1.0 + (docSet.size() / entry.getValue()));
            idf.put(entry.getKey(), idfScore);
        }
        /**
         * menyiapkan bobot(tf*idf) query
         */
        for (String term : vocabulary) {
            double freq;
            double TF;
            freq = Collections.frequency(query, term);
            if (freq == 0)
                TF = 0.0;
            else
                TF = 1 + Math.log10(freq);
            qVec.put(term, TF * idf.get(term));
        }

        /**
         * menghitung jarak vektor antara query
         * dan seluruh dokumen
         */
        for (Document doc : docSet) {
            TreeSet<String> union = new TreeSet<>();
            union.addAll(doc.getTokens());
            union.addAll(query);
            double w, qd = 0.0, d2 = 0.0, q2 = 0.0, cos;
            for (String term : union) {
                double freq;
                double dtf = 0;
                freq = Collections.frequency(doc.getTokens(), term);

                if (freq == 0.0)
                    dtf = 0.0;
                else
                    dtf = 1.0 + Math.log10(freq);

                w = dtf * idf.get(term);
                qd += w * qVec.get(term);
                q2 += Math.pow(qVec.get(term), 2.0);
                d2 += Math.pow(w, 2.0);
            }
            cos = qd / (Math.sqrt(q2) * Math.sqrt(d2));
            if (Double.isNaN(cos))
                cos = 0;
            SearchResponse docResult = new SearchResponse(doc, cos);
            if (cos != 0)
                listResult.add(docResult);
        }
        //perangkingan
        Collections.sort(listResult);
        return listResult;
    }

    public double[] getQueryTFIDF(String queryString) {
        query = tokenizer.tokenize(queryString);
        dfQ = new TreeMap<>();
        listResult = new ArrayList<>();
        for (String str : query) {
            double qfreq = 0.0;
            vocabulary.add(str);
            for (Document d : docSet) {
                if (d.getTokens().contains(str))
                    qfreq++;
            }
            dfQ.put(str, qfreq);
        }
        for (Map.Entry<String, Double> entry : dfQ.entrySet()) {
            double idfScore = 0.0;
            if (entry.getValue() != 0) {
                idfScore = Math.log10(1.0 + (docSet.size() / entry.getValue()));
            }
            idf.put(entry.getKey(), idfScore);
        }
        double[] qtf = new double[vocabulary.size()];
        int i = 0;
        for (String term : vocabulary) {
            double freq;
            double TF;
            freq = Collections.frequency(query, term);
            if (freq == 0)
                TF = 0.0;
            else
                TF = 1 + Math.log10(freq);
            qtf[i] = (TF * idf.get(term));
            i++;
        }
        return qtf;
    }

    public double[][] getTFIDF() {
        double[][] tfidf = new double[docSet.size()][vocabulary.size()];
        int i = 0;
        double w;
        for (Document doc : docSet) {
            int j = 0;
            for (String term : vocabulary) {
                double freq;
                double dtf;
                freq = Collections.frequency(doc.getTokens(), term);
                if (freq == 0)
                    dtf = 0.0;
                else
                    dtf = 1.0 + Math.log10(freq);
                w = (dtf * idf.get(term));
                tfidf[i][j] = w;
                j++;
            }
            i++;
        }
        return tfidf;
    }
}
