package id.ac.usd.ir.controller.api;

import id.ac.usd.ir.payload.IRResponse;
import id.ac.usd.ir.service.TFIDFSearcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ir")
public class IRControllerApi {

    @GetMapping
    public IRResponse get(){
        TFIDFSearcher t = new TFIDFSearcher();
        t.prepareTfidf(t.getDocuments());
        return new IRResponse(t.vocabulary, t.idf, t.df ,t.vocabulary.size(), t.getTFIDF());
    }
}
