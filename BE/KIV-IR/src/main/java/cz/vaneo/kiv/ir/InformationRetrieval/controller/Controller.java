package cz.vaneo.kiv.ir.InformationRetrieval.controller;


import cz.vaneo.kiv.ir.InformationRetrieval.model.Article;
import cz.vaneo.kiv.ir.InformationRetrieval.model.QueryRequest;
import cz.vaneo.kiv.ir.InformationRetrieval.model.QueryResponse;
import cz.vaneo.kiv.ir.InformationRetrieval.utils.FileHelper;
import cz.vaneo.kiv.ir.InformationRetrieval.utils.FileHelperJson;
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

        LOGGER.info("query request: " + queryRequest.getQuery());

        FileHelper fileHelper = new FileHelperJson();
        List<Article> articles2 = fileHelper.readFromFile();

        return new QueryResponse(queryRequest.getQuery(), new Date(), articles2);
    }
}

