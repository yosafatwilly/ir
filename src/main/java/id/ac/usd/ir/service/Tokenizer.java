package id.ac.usd.ir.service;

import id.ac.usd.ir.exception.ResourceNotFoundException;
import jsastrawi.morphology.DefaultLemmatizer;
import jsastrawi.morphology.Lemmatizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * @author Yosafat Willy Christian
 */
public class Tokenizer {

    Logger log = LoggerFactory.getLogger(Tokenizer.class);
    private  Set<String> rootwords = new HashSet<>(); //menyimpan daftar rootwords
    private Set<String> stopwords = new HashSet<>(); //menyimpan daftar stopwords
    private Lemmatizer lemmatizer = null; //library jsastrawi untuk stemming

    public Tokenizer() {
        loadRootWord();
        loadStopWords();
        log.info("Finish Load Stopwords & Rootwords...");
    }

    public List<String> tokenize(String content) {
        String text = content.toLowerCase();
        text = text.replaceAll("[^a-zA-Z0-9]", " ");
        String[] lines = text.split("\\s+");
        List<String> tokens = new Vector<>();
        for(String word: lines){
//            comment jika term dengan jumlah <=1 (EX: "a", "b", "1" ...) ingin dijadikan attribut
//            if(word.length() <= 1) continue;
            if(isStopword(word)) continue;
                word = stem(word);
            tokens.add(word);
        }
        return tokens;
    }

    private void loadRootWord(){
        try {
            InputStream in = Lemmatizer.class.
                    getResourceAsStream("/root-words.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                rootwords.add(line);
            }
        }catch (IOException a){
            throw new ResourceNotFoundException("root-words.txt not found.");
        }
        lemmatizer = new DefaultLemmatizer(rootwords);
    }

    private void loadStopWords() {
        stopwords = new HashSet<>();
        try {
            InputStream in = Tokenizer.class.
                    getResourceAsStream("/stopwords-id.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String word;
            while ((word = br.readLine()) != null) {
                if (word.trim().length() == 0)
                    continue;
                stopwords.add(word.trim());
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("stopwords-id.txt not found.");
        }
    }

    private boolean isStopword(String term) {
        return stopwords.contains(term.trim());
    }

    private String stem(String term) {
        return lemmatizer.lemmatize(term);
    }
}
