package cz.vaneo.kiv.ir.InformationRetrieval.controller;


import cz.vaneo.kiv.ir.InformationRetrieval.model.Article;
import cz.vaneo.kiv.ir.InformationRetrieval.model.QueryRequest;
import cz.vaneo.kiv.ir.InformationRetrieval.model.QueryResponse;
import cz.vaneo.kiv.ir.InformationRetrieval.preprocessing.*;
import cz.vaneo.kiv.ir.InformationRetrieval.utils.FileHelper;
import cz.vaneo.kiv.ir.InformationRetrieval.utils.FileHelperJson;
import cz.vaneo.kiv.ir.InformationRetrieval.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {

    Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    private static final String FILE_NAME = "articles_2.json";


    @PostMapping("/")
    public QueryResponse index(@RequestBody QueryRequest queryRequest) {

        LOGGER.info("query request: " + queryRequest.getQuery());

        FileHelper fileHelper = new FileHelperJson();
        ArrayList<Article> articles = fileHelper.readFromFile(FILE_NAME);

        return new QueryResponse(queryRequest.getQuery(), new Date(), articles);
    }

    @GetMapping("/add")
    public String add() {
        // check if data are indexed
        // TODO
        Preprocessing preprocessing = new BasicPreprocessing(
                new CzechStemmerAggressive(),
                new AdvancedTokenizer(),
                CzechStopWordsLoader.readStopWords("stopwords.txt"),
                false,
                true,
                true);



        LOGGER.info("Loading data from file " + FILE_NAME);

        FileHelper fileHelper = new FileHelperJson();
        List<Article> articles2 = fileHelper.readFromFile(FILE_NAME);

        preprocessing.index(articles2.get(0).getContent());
        Utils.printWordFrequencies(preprocessing.getWordFrequencies());


        LOGGER.info("Indexing...");

        return "Data successfully indexed";
    }

    @GetMapping("/delete")
    public String delete() {
        LOGGER.info("Clearing app memory...");
        return "Data successfully deleted from app";
    }
}

