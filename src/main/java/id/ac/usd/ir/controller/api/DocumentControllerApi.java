package id.ac.usd.ir.controller.api;

import id.ac.usd.ir.entity.Document;
import id.ac.usd.ir.service.FCMSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yosafat Willy Christian
 */
@RestController
@RequestMapping("/api/document")
public class DocumentControllerApi {

    private FCMSearcher fcmSearcher;

    @Autowired
    public DocumentControllerApi(FCMSearcher fcmSearcher) {
        this.fcmSearcher = fcmSearcher;
    }

    @GetMapping
    public List<Document> getDocuments() {
        return fcmSearcher.getDocuments();
    }

    @GetMapping("/{id}")
    public Document getDocumentById(@PathVariable(name = "id") int id) {
        return fcmSearcher.docById(id);
    }
}
