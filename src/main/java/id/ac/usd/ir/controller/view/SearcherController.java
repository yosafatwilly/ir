package id.ac.usd.ir.controller.view;

import id.ac.usd.ir.payload.CustomSearchResponse;
import id.ac.usd.ir.payload.SearchResponse;
import id.ac.usd.ir.service.FCMSearcher;
import id.ac.usd.ir.service.TFIDFSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SearcherController {

    private FCMSearcher fcmSearcher;

    private TFIDFSearcher tfidfSearcher;

    @Autowired
    public SearcherController(FCMSearcher fcmSearcher, TFIDFSearcher tfidfSearcher) {
        this.fcmSearcher = fcmSearcher;
        this.tfidfSearcher = tfidfSearcher;
    }

    @PostMapping("/search")
    public ModelAndView search(HttpServletRequest request) {
        long start;
        ModelAndView model = new ModelAndView("results");
        model.addObject("title", "Results");
        String query = request.getParameter("query");
        String rb = request.getParameter("fcm");
        model.addObject("query", query);
        CustomSearchResponse results = new CustomSearchResponse();
        if (rb.equalsIgnoreCase("1")){
            if (fcmSearcher.getClusterResult() == null){
                return new ModelAndView("error/error_cluster");
            }
            start = System.currentTimeMillis();
            List<SearchResponse> list = fcmSearcher.search(query);
            results.setResults(list);
            results.setTimeExecute(System.currentTimeMillis() - start);
            model.addObject("type", "FCM Cluster Based");
        }else{
            start = System.currentTimeMillis();
            tfidfSearcher.prepareTfidf(tfidfSearcher.getDocuments());
            List<SearchResponse> list = tfidfSearcher.search(query);
            results.setResults(list);
            results.setTimeExecute(System.currentTimeMillis() - start);
            model.addObject("type", "NON FCM Cluster Based");
        }
        model.addObject("results", results);
        return model;
    }
}
