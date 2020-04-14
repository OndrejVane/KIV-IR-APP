package cz.vaneo.kiv.ir.InformationRetrieval.controller;


import cz.vaneo.kiv.ir.InformationRetrieval.model.Article;
import cz.vaneo.kiv.ir.InformationRetrieval.model.QueryRequest;
import cz.vaneo.kiv.ir.InformationRetrieval.model.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {

    Logger LOGGER = LoggerFactory.getLogger(Controller.class);


    @PostMapping("/")
    public QueryResponse index(@RequestBody QueryRequest queryRequest) {

        LOGGER.info("INFO");
        LOGGER.warn("WARN");
        LOGGER.error("ERROR");
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("Hello text from article 1"));
        articles.add(new Article("Hello text from article 2"));
        return new QueryResponse(queryRequest.getQuery(), new Date(), articles);
    }
}

