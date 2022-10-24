package id.ac.usd.ir.service;

import id.ac.usd.ir.entity.Document;
import id.ac.usd.ir.exception.ResourceNotFoundException;
import id.ac.usd.ir.payload.SearchResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author Yosafat Willy Christian
 */
public abstract class Searcher {

    static Tokenizer tokenizer = new Tokenizer();
    private List<Document> documents;

    public Searcher() {
        this.documents = parseDocumentFromDirectory("100berita");
//        this.documents = parseDocumentFromFile("/data/10judul.txt");
//        this.documents = parseDocumentFromFile("/data/6judul.txt");
    }

    public static List<Document> parseDocumentFromDirectory(String directory) {
        List<Document> documents = new Vector<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath:data/"+directory+"/*.*");
            for (Resource resource : resources) {
                InputStream inStream = resource.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
                String line, input = "";
                while ((line = br.readLine()) != null)
                    input = input.concat(line+" ");

                String fileName = resource.getFilename().replaceAll(".txt", "");
                String[] parts = fileName.split("-");
                String judul = parts[2].trim();
                String kategori = parts[1].trim();
                String ids = parts[0].trim();
                input = input.concat(judul);

                List<String> tokens = tokenizer.tokenize(input);
                Document doc = new Document(Integer.parseInt(ids),judul, kategori, input, tokens,null);
                documents.add(doc);
            }
        } catch (IOException e) {
            throw new ResourceNotFoundException("Document", "directory", directory);
        }
        return documents;
    }

    public List<Document> parseDocumentFromFile(String fileName) {
        List<Document> documents = new Vector<>();
        try {
            InputStream in = Searcher.class.getResourceAsStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\t");
                int id = Integer.parseInt(parts[0]);
                String rawText = parts[1];
                List<String> tokens = tokenizer.tokenize(rawText);
                Document doc = new Document(id, rawText, "Uncategorised", rawText, tokens, null);
                documents.add(doc);
            }
        } catch (IOException a) {
            throw new ResourceNotFoundException("Document tidak ditemukan");
        }
        this.documents = documents;
        return documents;
    }

    public Document docById(int id){
        for (Document doc : documents) {
            if (doc.getId() == id){
                return doc;
            }
        }
        return null;
    }

    public List<Document> docByCategory(String cat){
        List<Document> res = new ArrayList<>();
        for (Document c : documents){
            if (cat.equalsIgnoreCase(c.getKategori())){
                res.add(c);
            }
        }
        return res;
    }

    abstract public List<SearchResponse> search(String queryString);

    public List<Document> getDocuments() {
        return documents;
    }
}
