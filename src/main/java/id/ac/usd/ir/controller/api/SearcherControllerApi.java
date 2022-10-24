package id.ac.usd.ir.controller.api;

import id.ac.usd.ir.payload.CustomSearchResponse;
import id.ac.usd.ir.payload.SearchResponse;
import id.ac.usd.ir.service.FCMSearcher;
import id.ac.usd.ir.service.TFIDFSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yosafat Willy Christian
 */
@RestController
@RequestMapping("/api")
public class SearcherControllerApi {

    private FCMSearcher fcmSearcher;

    private TFIDFSearcher tfidfSearcher;

    @Autowired
    public SearcherControllerApi(FCMSearcher fcmSearcher, TFIDFSearcher tfidfSearcher) {
        this.fcmSearcher = fcmSearcher;
        this.tfidfSearcher = tfidfSearcher;
    }

    @GetMapping("/fcm/search")
    public CustomSearchResponse fcm(@RequestParam(value = "query", required = true) String query) {
        long start = System.currentTimeMillis();
        List<SearchResponse> list = fcmSearcher.search(query);
        return new CustomSearchResponse(list, System.currentTimeMillis() - start);
    }

    @GetMapping("/tfidf/search")
    public CustomSearchResponse tfidf(@RequestParam(value = "query", required = true) String query) {
        long start = System.currentTimeMillis();
        tfidfSearcher.prepareTfidf(tfidfSearcher.getDocuments());
        List<SearchResponse> list = tfidfSearcher.search(query);
        return new CustomSearchResponse(list, System.currentTimeMillis() - start);
    }


}