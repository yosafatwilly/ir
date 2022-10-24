package id.ac.usd.ir.service;

import id.ac.usd.ir.entity.Document;
import id.ac.usd.ir.payload.FCMRequest;
import id.ac.usd.ir.payload.FCMResponse;
import id.ac.usd.ir.payload.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FCMSearcher extends Searcher {

    private Logger log = LoggerFactory.getLogger(FCMSearcher.class);

    //untuk mempersiapkn bobot seluruh dokumen
    private TFIDFSearcher tfidfSearcher;

    //untuk mengelompokkan dokumen
    private FCMCluster fcmCluster;

    //menyimpan hasil pengelompokkan, berupa nomor cluster berserta dokumennya
    private HashMap<Integer, List<Document>> clusterResult;

    //menyimpan perhitungan DF dan IDF untuk setiap cluster
    private HashMap<Integer, TFIDFSearcher> tfidfSearcherHashMap = new HashMap<>();

    @Autowired
    public FCMSearcher(TFIDFSearcher tfidfSearcher) {
        this.tfidfSearcher = tfidfSearcher;
    }

    public FCMResponse doCluster(FCMRequest fcmRequest) {
        tfidfSearcher.prepareTfidf(getDocuments());
        fcmCluster = new FCMCluster(tfidfSearcher.getTFIDF(), fcmRequest);
        clusterResult = new HashMap<>();
        for (int i = 0; i < fcmCluster.getU().length; i++) {
            double max = 0, tmp = 0;
            int h = 0;
            for (int k = 0; k < fcmCluster.getC(); k++) {
                max = Math.max(fcmCluster.getU()[i][k], max);
                if (tmp != max)
                    h = k + 1;
                tmp = max;
            }
            getDocuments().get(i).setDerajadKeanggotaan(fcmCluster.getU()[i]);
            if (!clusterResult.containsKey(h)) {
                List<Document> docList = new ArrayList<>();
                docList.add(getDocuments().get(i));
                clusterResult.put(h, docList);
            } else {
                clusterResult.get(h).add(getDocuments().get(i));
            }
        }
        for (Map.Entry<Integer, List<Document>> entry : clusterResult.entrySet()) {
            TFIDFSearcher tfidfSearcher = new TFIDFSearcher();
            tfidfSearcher.prepareTfidf(entry.getValue());
            tfidfSearcherHashMap.put((entry.getKey()), tfidfSearcher);
            log.info("Finish prepare DF & IDF for cluster " + entry.getKey());
        }
        return new FCMResponse(fcmCluster.getM(), fcmCluster.getN(),
                fcmCluster.getV(), clusterResult);
    }

    public List<SearchResponse> search(String queryString) {
        //menghitung bobot query
        double[] termtf = tfidfSearcher.getQueryTFIDF(queryString);
        //memprediksi cluster
        int cluster = fcmCluster.predict(termtf);
        if (cluster == -1)
            return null;
        List<SearchResponse> sr;
        try {
            //melakukan pencarian pada cluster yang dipilih
            sr = tfidfSearcherHashMap.get(cluster).search(queryString);
        } catch (Exception e) {
            return null;
        }
        return sr;
    }

    public HashMap<Integer, List<Document>> getClusterResult() {
        return clusterResult;
    }
}
