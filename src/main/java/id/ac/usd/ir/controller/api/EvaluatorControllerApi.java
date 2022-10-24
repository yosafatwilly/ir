package id.ac.usd.ir.controller.api;

import id.ac.usd.ir.payload.evaluator.EvaluatorResponse;
import id.ac.usd.ir.payload.evaluator.QueryRelevance;
import id.ac.usd.ir.service.FCMSearcher;
import id.ac.usd.ir.service.Searcher;
import id.ac.usd.ir.service.SearcherEvaluator;
import id.ac.usd.ir.service.TFIDFSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/evaluator")
public class EvaluatorControllerApi {

    private SearcherEvaluator searcherEvaluator;

    private FCMSearcher fcmSearcher;

    private TFIDFSearcher tfidfSearcher;

    @Autowired
    public EvaluatorControllerApi(FCMSearcher fcmSearcher, TFIDFSearcher tfidfSearcher, SearcherEvaluator searcherEvaluator) {
        this.fcmSearcher = fcmSearcher;
        this.tfidfSearcher = tfidfSearcher;
        this.searcherEvaluator = searcherEvaluator;
    }

    @GetMapping("/tfidf")
    public EvaluatorResponse getRPTFIDFSearcher(){
        tfidfSearcher.prepareTfidf(tfidfSearcher.getDocuments());
        Searcher s = tfidfSearcher;
        return searcherEvaluator.getEvaluate(s);
    }

    @GetMapping("/fcm")
    public ResponseEntity<?> getRPFCMSearcher(){
        if (fcmSearcher.getClusterResult() == null){
            return ResponseEntity.badRequest().body("Silahkan Cluster terlebih dahulu");
        }
        Searcher s = fcmSearcher;
        return ResponseEntity.ok().body(searcherEvaluator.getEvaluate(s));
    }

    @GetMapping("/queryrelevance")
    public List<QueryRelevance> getQueryAndRelevansDocs(){
        return searcherEvaluator.getQueryRelevances();
    }
}
