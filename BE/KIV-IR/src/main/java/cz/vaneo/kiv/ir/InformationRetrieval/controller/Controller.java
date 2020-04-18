package cz.vaneo.kiv.ir.InformationRetrieval.controller;


import cz.vaneo.kiv.ir.InformationRetrieval.model.Article;
import cz.vaneo.kiv.ir.InformationRetrieval.model.QueryRequest;
import cz.vaneo.kiv.ir.InformationRetrieval.model.QueryResponse;
import cz.vaneo.kiv.ir.InformationRetrieval.preprocessing.*;
import cz.vaneo.kiv.ir.InformationRetrieval.service.ArticleManager;
import cz.vaneo.kiv.ir.InformationRetrieval.utils.FileHelper;
import cz.vaneo.kiv.ir.InformationRetrieval.utils.FileHelperJson;
import cz.vaneo.kiv.ir.InformationRetrieval.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {

    Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    private static final String FILE_NAME = "articles_2.json";

    @Autowired
    ArticleManager articleManager;

    /**
     * Load all articles from file and store to article manager
     *
     * @return message with number of successfully added articles
     */
    @GetMapping("/init")
    public String init() {
        LOGGER.info("GET /init => init()");
        FileHelper fileHelper = new FileHelperJson();
        ArrayList<Article> articles = fileHelper.readFromFile(FILE_NAME);

        for (Article article : articles) {
            articleManager.addArticle(article);
        }

        return articles.size() + " articles has been stored to article manager";

    }

    /**
     * Get all articles stored in article manager
     *
     * @return article
     */
    @GetMapping("/article")
    public List<Article> getAllArticles() {
        LOGGER.info("GET /article => getAllArticles()");
        return articleManager.getAllArticles();
    }

    /**
     * Add article to the article manager
     *
     * @param article new article
     * @return logical value that represent if the article has been added to the article manager
     */
    @PostMapping("/article")
    public boolean addArticle(@RequestBody Article article) {
        LOGGER.info("POST /article => addArticle(article)");
        return articleManager.addArticle(article);
    }

    /**
     * Get article with given id
     *
     * @param id integer value that represents id of article
     * @return found article | null
     */
    @GetMapping("/article/{id}")
    public Article getArticleById(@PathVariable int id) {
        LOGGER.info("GET /article/{id} => getArticleById(id)");
        return articleManager.getArticle(id);
    }

    /**
     * Delete all articles from article manager
     *
     * @return logical value that represent if all articles has been deleted from article manager
     */
    @DeleteMapping("/article")
    public boolean deleteAllArticles() {
        LOGGER.info("DELETE /article => deleteAllArticles()");
        return articleManager.deleteAllArticles();
    }

    /**
     * Delete article with given id
     *
     * @param id id of article
     * @return logical value that represent if the article with id has been deleted from article manager
     */
    @DeleteMapping("/article/{id}")
    public boolean deleteArticleWithId(@PathVariable int id) {
        LOGGER.info("DELETE /article/{id} => deleteArticleWithId(id)");
        return articleManager.deleteArticle(id);
    }

    /**
     * Search method that receive query and search query in all articles.
     *
     * @param queryRequest query to search
     * @return response with all found articles
     */
    @PostMapping("/")
    public QueryResponse searchQuery(@RequestBody QueryRequest queryRequest) {
        LOGGER.info("POST / => searchQuery(queryRequest)");

        LOGGER.info("Searching: " + queryRequest.getQuery());

        return new QueryResponse(queryRequest.getQuery(), new Date(), articleManager.getAllArticles());
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


}

