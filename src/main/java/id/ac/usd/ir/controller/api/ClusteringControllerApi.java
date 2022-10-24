package id.ac.usd.ir.controller.api;

import id.ac.usd.ir.payload.FCMRequest;
import id.ac.usd.ir.payload.FCMResponse;
import id.ac.usd.ir.service.FCMCluster;
import id.ac.usd.ir.service.FCMSearcher;
import id.ac.usd.ir.service.TFIDFSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yosafat Willy Christian
 */
@RestController
@RequestMapping("/api/cluster")
public class ClusteringControllerApi {

    private FCMSearcher fcmSearcher;

    private TFIDFSearcher tfidfSearcher;

    @Autowired
    public ClusteringControllerApi(FCMSearcher fcmSearcher, TFIDFSearcher tfidfSearcher) {
        this.fcmSearcher = fcmSearcher;
        this.tfidfSearcher = tfidfSearcher;
    }

    @PostMapping("/fcm")
    public FCMResponse doCluster(@RequestBody FCMRequest fcmRequest) throws InterruptedException {
        return fcmSearcher.doCluster(fcmRequest);
    }

    @GetMapping("/test")
    public double[][] getU(){
        tfidfSearcher.prepareTfidf(tfidfSearcher.getDocuments());
        FCMRequest fcmRequest = new FCMRequest();
        fcmRequest.setJumlahCluster(2);
        fcmRequest.setPangkat(2);
        fcmRequest.setEpsilon(0.000001);
        fcmRequest.setMaxIter(100);
        FCMCluster fcmClustering = new FCMCluster(tfidfSearcher.getTFIDF(), fcmRequest);
        return fcmClustering.getU();
    }

}
