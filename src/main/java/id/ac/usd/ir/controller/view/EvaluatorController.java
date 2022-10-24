package id.ac.usd.ir.controller.view;

import id.ac.usd.ir.payload.evaluator.EvaluatorResponse;
import id.ac.usd.ir.service.FCMSearcher;
import id.ac.usd.ir.service.SearcherEvaluator;
import id.ac.usd.ir.service.TFIDFSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/evaluation")
public class EvaluatorController {

    private SearcherEvaluator searcherEvaluator;

    private FCMSearcher fcmSearcher;

    private TFIDFSearcher tfidfSearcher;

    @Autowired
    public EvaluatorController(FCMSearcher fcmSearcher, TFIDFSearcher tfidfSearcher, SearcherEvaluator searcherEvaluator) {
        this.fcmSearcher = fcmSearcher;
        this.tfidfSearcher = tfidfSearcher;
        this.searcherEvaluator = searcherEvaluator;
    }

    @GetMapping("/tfidf")
    public ModelAndView getEvaluationTFIDF(){
        tfidfSearcher.prepareTfidf(tfidfSearcher.getDocuments());
        ModelAndView model = new ModelAndView("evaluate");
        model.addObject("title", "Evaluation");
        model.addObject("evaluate", searcherEvaluator.getEvaluate(tfidfSearcher));
        model.addObject("type", "Tanpa Cluster");
        return model;
    }

    @GetMapping("/fcm")
    public ModelAndView getEvaluationFCM(){
        if (fcmSearcher.getClusterResult() == null) {
            return new ModelAndView("error/error_cluster");
        }
        ModelAndView model = new ModelAndView("evaluate");
        model.addObject("title", "Evaluation");
        model.addObject("evaluate", searcherEvaluator.getEvaluate(fcmSearcher));
        model.addObject("type", "Berbasis Cluster");
        return model;
    }

    @GetMapping("/compare")
    public ModelAndView compareEvaluation(){
        if (fcmSearcher.getClusterResult() == null) {
            return new ModelAndView("error/error_cluster");
        }
        tfidfSearcher.prepareTfidf(tfidfSearcher.getDocuments());
        ModelAndView model = new ModelAndView("evaluate_compare");
        model.addObject("title", "Compare");
        List<EvaluatorResponse> evaluatorResponses = new ArrayList<>();
        evaluatorResponses.add(searcherEvaluator.getEvaluate(tfidfSearcher));
        evaluatorResponses.add(searcherEvaluator.getEvaluate(fcmSearcher));
        model.addObject("ev", evaluatorResponses);
        return model;
    }

    @GetMapping("/relevan")
    public ModelAndView getQueryAndRelevansDocs(){
        ModelAndView model = new ModelAndView("query_relevant_docs");
        model.addObject("title", "Query & Relevant Docs");
        model.addObject("query", searcherEvaluator.getQueryRelevances());
        return model;
    }
}
