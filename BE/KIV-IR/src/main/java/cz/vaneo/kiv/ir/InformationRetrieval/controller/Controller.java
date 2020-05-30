package cz.vaneo.kiv.ir.InformationRetrieval.controller;


import cz.vaneo.kiv.ir.InformationRetrieval.core.data.ArticleRepository;
import cz.vaneo.kiv.ir.InformationRetrieval.core.data.ArticleRepositoryImpl;
import cz.vaneo.kiv.ir.InformationRetrieval.core.data.Document;
import cz.vaneo.kiv.ir.InformationRetrieval.core.data.Result;
import cz.vaneo.kiv.ir.InformationRetrieval.core.model.Article;
import cz.vaneo.kiv.ir.InformationRetrieval.core.model.Message;
import cz.vaneo.kiv.ir.InformationRetrieval.core.model.QueryResult;
import cz.vaneo.kiv.ir.InformationRetrieval.core.searching.Index;
import cz.vaneo.kiv.ir.InformationRetrieval.core.utils.IOUtils;
import cz.vaneo.kiv.ir.InformationRetrieval.model.QueryRequest;
import cz.vaneo.kiv.ir.InformationRetrieval.model.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {

    private static final String FILE_NAME = "test_3.json";
    private static final int NUMBER_OF_HITS = 10;
    Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    ArticleRepository articleRepository = new ArticleRepositoryImpl();
    Index index = new Index();

    /**
     * Načte všechny články ze souboru a uloží je do paměti.
     *
     * @return zpráva o úspěšném načtení
     */
    @GetMapping("/init")
    public Message init() {

        List<Article> articles = IOUtils.readArticlesFromFile(FILE_NAME);

        articleRepository.addNewArticles(articles);

        long startTime = System.nanoTime();
        index.index(articleRepository.getArticlesAsDocument());

        return new Message(true, "All articles "
                + articles.size() + " successfully idexed in " + (System.nanoTime() - startTime)/ 1000000000 + "s");
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
        
        QueryResult results = index.search(queryRequest.getQuery(), NUMBER_OF_HITS);
        List<cz.vaneo.kiv.ir.InformationRetrieval.model.Article> articles = new ArrayList<>();
        for (Result result : results.getResults()) {
            cz.vaneo.kiv.ir.InformationRetrieval.model.Article article = new cz.vaneo.kiv.ir.InformationRetrieval.model.Article();
            article.setId(Integer.parseInt(result.getDocumentID()));
            article.setRank(result.getRank());
            article.setScore(result.getScore());
            Article article1 = articleRepository.getArticleById(result.getDocumentID());
            article.setTitle(article1.getTitle());
            article.setAuthor(article1.getAuthor());
            article.setContent(article1.getContent());
            article.setUrl(article1.getUrl());
            article.setDownloadedDate(article1.getDownloadedDate());
            articles.add(article);
        }

        return new QueryResponse(articles, results.getNumberOfFoundDocuments(), queryRequest.getQuery());
    }

    /**
     * Přidání nového článku a zaindexování do stávajícího indexu.
     *
     * @param article nový článek
     * @return logická honodnota, že vše dopadlo vpořádku
     */
    @PostMapping("/article")
    public boolean addArticle(@RequestBody Article article) {
        LOGGER.info("POST /article => addArticle(article)");
        int docId = articleRepository.addNewArticle(article);
        Document newDoc = articleRepository.getArticleAsDocumentById(docId);

        index.index(newDoc);

        return true;
    }

    /**
     * Get all articles stored in article manager
     *
     * @return article
     */
    @GetMapping("/article")
    public List<Article> getAllArticles() {
        LOGGER.info("GET /article => getAllArticles()");
        return articleRepository.getAllArticles();
    }


}

