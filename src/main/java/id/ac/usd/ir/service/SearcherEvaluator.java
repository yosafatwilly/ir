package id.ac.usd.ir.service;

import id.ac.usd.ir.entity.Document;
import id.ac.usd.ir.payload.SearchResponse;
import id.ac.usd.ir.payload.evaluator.Evaluator;
import id.ac.usd.ir.payload.evaluator.EvaluatorResponse;
import id.ac.usd.ir.payload.evaluator.QueryRelevance;
import id.ac.usd.ir.payload.evaluator.RecallPrecision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class SearcherEvaluator{

    private Logger log = LoggerFactory.getLogger(SearcherEvaluator.class);
    private List<QueryRelevance> queryRelevances;
    private double[] stdRec = {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
    private TFIDFSearcher tfidfSearcher;

    @Autowired
    public SearcherEvaluator(TFIDFSearcher tfidfSearcher) {
        this.tfidfSearcher = tfidfSearcher;
        String file = "/evaluation/100berita.txt";
//        String file = "/evaluation/10judul.txt";
        parseQueryAnswersFromFile(file);
    }

    private void parseQueryAnswersFromFile(String file){
        this.queryRelevances = new ArrayList<>();
        try {
            InputStream in = SearcherEvaluator.class.getResourceAsStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(":");
                String query = parts[0].trim();
                String[] docIDs = parts[1].trim().split("\\s+");
                Set<Integer> relDocIDs = new HashSet<>();
                List<Document> documents = new ArrayList<>();
                for (String docID : docIDs) {
                    int id = Integer.parseInt(docID);
                    relDocIDs.add(id);
                    documents.add(tfidfSearcher.docById(id));
                }
                queryRelevances.add(new QueryRelevance(query, documents, relDocIDs));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EvaluatorResponse getEvaluate(Searcher searcher) {
        log.info("Get List Recall Precision");
        List<Evaluator> evaluators = new ArrayList<>();
        List<SearchResponse> listR;
        Map<String, Double> averageRP = new HashMap<>();
        double rSum = 0.0;
        double pSum = 0.0;
        for (QueryRelevance queryRelevance : queryRelevances) {
            long start = System.currentTimeMillis();
            listR = searcher.search(queryRelevance.getQuery());
            long res = System.currentTimeMillis() - start;
            if (listR == null) {
                return null;
            }
            Evaluator evaluator = new Evaluator();
            evaluator.setQuery(queryRelevance.getQuery());
            evaluator.setInterpolation(getInterpolation(listR, queryRelevance.getAnswers()));
            evaluator.setRecallPrecision(getListRecallPrecision(listR, queryRelevance.getAnswers()));
            evaluator.setTime(res);
            evaluator.setRelevantCount(queryRelevance.getAnswers().size());
            //Average
            rSum += getRecall(listR, queryRelevance.getAnswers());
            pSum += getPrecision(listR, queryRelevance.getAnswers());
            evaluators.add(evaluator);
        }
        averageRP.put("recall", rSum / queryRelevances.size());
        averageRP.put("precision", pSum / queryRelevances.size());

        return new EvaluatorResponse(evaluators, averageRP);
    }

    private double getRecall(List<SearchResponse> listR, Set<Integer> queryAnswers) {
        double r;
        double count = 0.0;
        for (SearchResponse searchResponse : listR) {
            if (queryAnswers.contains(searchResponse.getDocument().getId())) {
                count++;
            }
        }
        r = count / queryAnswers.size();
        return r;
    }

    private double getPrecision(List<SearchResponse> listR, Set<Integer> queryAnswers) {
        double p;
        double count = 0.0;
        for (SearchResponse searchResponse : listR) {
            if (queryAnswers.contains(searchResponse.getDocument().getId())) {
                count++;
            }
        }
        p = count / listR.size();
        return p;
    }

    private List<RecallPrecision> getListRecallPrecision(List<SearchResponse> listR, Set<Integer> queryAnswers) {
        List<RecallPrecision> recallPrecisions = new ArrayList<>();
        double precision, recall;
        double count = 0.0;
        for (int i = 0; i < listR.size(); i++) {
            RecallPrecision recallPrecision = new RecallPrecision();
            if (queryAnswers.contains(listR.get(i).getDocument().getId())) {
                count++;
                recallPrecision.setRelevan(true);
            } else {
                recallPrecision.setRelevan(false);
            }
            recall = count / queryAnswers.size();
            precision = count / (i + 1);
            recallPrecision.setRecall(recall);
            recallPrecision.setPrecision(precision);
            recallPrecision.setDocument(listR.get(i).getDocument());
            recallPrecisions.add(recallPrecision);
        }
        return recallPrecisions;
    }

    private TreeMap<Double, Double> getInterpolation(List<SearchResponse> listR, Set<Integer> queryAnswers) {
        TreeMap<Double, Double> p = new TreeMap<>();
        for (int j = 0; j < stdRec.length; j++) {
            double precision, recall;
            double count = 0.0;
            for (int i = 0; i < listR.size(); i++) {
                if (queryAnswers.contains(listR.get(i).getDocument().getId())) {
                    count++;
                    recall = count / queryAnswers.size();
                    precision = count / (i + 1);
                    if (recall == stdRec[j]) {
                        p.put(recall, precision);
                    } else if (stdRec[j] <= recall && recall < stdRec[j + 1]) {
                        p.put(stdRec[j], precision);
                    }
                }
            }
        }
        for (int j = 10; j >= 0; j--) {
            if (!p.containsKey(stdRec[j])) {
                if (stdRec[j] == 1.0) {
                    p.put(stdRec[j], 0.0);
                } else {
                    p.put(stdRec[j], p.get(stdRec[j + 1]));
                }
            }
        }
        return p;
    }

    public List<QueryRelevance> getQueryRelevances() {
        return queryRelevances;
    }
}