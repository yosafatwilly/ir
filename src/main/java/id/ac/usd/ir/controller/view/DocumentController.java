package id.ac.usd.ir.controller.view;

import id.ac.usd.ir.entity.Document;
import id.ac.usd.ir.service.FCMSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;

@Controller
public class DocumentController {

    private FCMSearcher fcmSearcher;

    @Autowired
    public DocumentController(FCMSearcher fcmSearcher) {
        this.fcmSearcher = fcmSearcher;
    }

    @GetMapping("/docs")
    public ModelAndView getAllDocuments() {
        ModelAndView model = new ModelAndView("docs");
        model.addObject("title", "Documents");
        model.addObject("cat", category());
        model.addObject("list", fcmSearcher.getDocuments());
        return model;
    }

    @GetMapping("/docs/{cat}")
    public ModelAndView getDocByCategory(@PathVariable String cat) {
        ModelAndView model = new ModelAndView("docs");
        model.addObject("title", "Documents");
        model.addObject("active", cat);
        model.addObject("cat", category());
        model.addObject("list", fcmSearcher.docByCategory(cat));
        return model;
    }

    @GetMapping("/doc/{id}")
    public ModelAndView getDocsById(@PathVariable int id) {
        ModelAndView model = new ModelAndView("doc");
        model.addObject("title", "Documents");
        if (fcmSearcher.docById(id) != null)
            model.addObject("document", fcmSearcher.docById(id));
        else
            return new ModelAndView("error/error_doc").addObject("id", id);
        return model;
    }

    public HashSet<String> category(){
        HashSet<String> cat = new HashSet<>();
        for (Document c : fcmSearcher.getDocuments()){
            cat.add(c.getKategori());
        }
        return cat;
    }
}
