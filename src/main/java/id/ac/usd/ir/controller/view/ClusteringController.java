package id.ac.usd.ir.controller.view;

import id.ac.usd.ir.payload.FCMRequest;
import id.ac.usd.ir.payload.FCMResponse;
import id.ac.usd.ir.service.FCMSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClusteringController {

    private FCMSearcher fcmSearcher;

    /**
     * Variable bantuan
     */
    private FCMResponse fcmResponse = new FCMResponse();
    private FCMRequest currentFcmRequest = new FCMRequest();
    private boolean isProcessing = false;

    @Autowired
    public ClusteringController(FCMSearcher fcmSearcher) {
        this.fcmSearcher = fcmSearcher;
    }

    @GetMapping("/cluster")
    public ModelAndView getClusterPage() {
        ModelAndView model = new ModelAndView("cluster");
        model.addObject("title", "Clustering");
        model.addObject("current", currentFcmRequest);
        model.addObject("isProcessing", isProcessing);
        model.addObject("result", fcmResponse);
        return model;
    }

    @GetMapping("/cluster/{num}")
    public ModelAndView getClusterByNumber(@PathVariable int num) {
        ModelAndView model = new ModelAndView("cluster");
        model.addObject("title", "Clustering");
        model.addObject("num", num);
        model.addObject("result", fcmResponse);
        model.addObject("active", num);
        model.addObject("current", currentFcmRequest);
        model.addObject("isProcessing", isProcessing);
        model.addObject("doclist", fcmResponse.getClusterResult().get(num));
        return model;
    }

    @PostMapping("/cluster")
    public ModelAndView doCluster(@ModelAttribute("fcmRequest") FCMRequest fcmRequest){
        if (!isProcessing){
            isProcessing = true;
            fcmResponse = fcmSearcher.doCluster(fcmRequest);
            ModelAndView model = new ModelAndView("cluster");
            model.addObject("title", "Clustering");
            currentFcmRequest = fcmRequest;
            model.addObject("current", currentFcmRequest);
            model.addObject("result", fcmResponse);
            isProcessing = false;
            return model;
        }else{
            return new ModelAndView("redirect:/cluster");
        }
    }
}
