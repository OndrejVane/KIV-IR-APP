package cz.zcu.kiv.nlp.ir.trec.controller;


import cz.zcu.kiv.nlp.ir.trec.core.utils.SerializedDataHelper;
import cz.zcu.kiv.nlp.ir.trec.data.*;
import cz.zcu.kiv.nlp.ir.trec.core.model.Article;
import cz.zcu.kiv.nlp.ir.trec.api.Message;
import cz.zcu.kiv.nlp.ir.trec.core.model.QueryResult;
import cz.zcu.kiv.nlp.ir.trec.core.searching.Index;
import cz.zcu.kiv.nlp.ir.trec.core.searching.SearchModel;
import cz.zcu.kiv.nlp.ir.trec.core.utils.IOUtils;
import cz.zcu.kiv.nlp.ir.trec.api.QueryRequest;
import cz.zcu.kiv.nlp.ir.trec.api.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {

    private static final String FILE_NAME = "articles.json";
    private static final String INDEX_FILE_NAME = "file_index";
    private static final String ARTICLES_FILE_NAME = "file_articles";
    private static final int NUMBER_OF_HITS = 20;
    static final String OUTPUT_DIR = "./TREC";
    Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    ArticleRepository articleRepository = new ArticleRepositoryImpl();
    Index index = new Index();

    /**
     * Načte všechny články ze souboru "articles.json" a zaindexuje.
     *
     * @return zpráva o úspěšném načtení
     */
    @GetMapping("/initmydata")
    public Message initMyData() {
        LOGGER.info("GET / => init()");

        LOGGER.info("Cleaning index and repository");
        index = new Index();
        articleRepository = new ArticleRepositoryImpl();


        List<Article> articles = IOUtils.readArticlesFromFile(FILE_NAME);

        articleRepository.addNewArticles(articles);

        long startTime = System.nanoTime();
        index.index(articleRepository.getArticlesAsDocument());

        return new Message(true, "All articles "
                + articles.size() + " successfully idexed in " + (System.nanoTime() - startTime)/ 1000000000 + "s");
    }

    /**
     * Načte všechny články ze souboru "czechData.bin" a zaindexuje je.
     *
     * @return zpráva o úspěšném načtení
     */
    @GetMapping("/initevaldata")
    public Message initEvalData() {
        LOGGER.info("GET / => init()");

        LOGGER.info("Cleaning index and repository");
        index = new Index();
        articleRepository = new ArticleRepositoryImpl();

        File serializedData = new File(OUTPUT_DIR + "/czechData.bin");

        List<Document> documents = new ArrayList<Document>();
        List<Article> articles = new ArrayList<>();
        LOGGER.info("loading...");
        try {
            if (serializedData.exists()) {
                documents = SerializedDataHelper.loadDocument(serializedData);
            } else {
                LOGGER.error("Cannot find " + serializedData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Document document : documents ) {
            Article article = new Article(new Date().toString(), document.getTitle(), "Author", document.getText(), "www.url.cz");
            articles.add(article);
        }


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

        if(queryRequest.getQuery() == null){
            return new QueryResponse(new ArrayList<>(), 0, "");
        }
        
        QueryResult results = index.search(queryRequest.getQuery(), NUMBER_OF_HITS);
        List<cz.zcu.kiv.nlp.ir.trec.api.Article> articles = new ArrayList<>();
        for (Result result : results.getResults()) {
            cz.zcu.kiv.nlp.ir.trec.api.Article article = new cz.zcu.kiv.nlp.ir.trec.api.Article();
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
     * Získání všech zaindexovaných dokumentů
     *
     * @return articles
     */
    @GetMapping("/article")
    public List<Article> getAllArticles() {
        LOGGER.info("GET /article => getAllArticles()");
        return articleRepository.getAllArticles();
    }

    /**
     * Nastavování vyhledávácího modelu
     *
     * @param isVectorModel logiská hodnota, která říká o jaký model se jedná (true = vectorModel, false = booleanModel)
     *
     * @return návrat vstupní hodnoty
     */
    @PostMapping("/set")
    public boolean setIndexModel(@RequestBody boolean isVectorModel) {
        LOGGER.info("POST /set => setIndexModel(isVectorModel)");
        if(isVectorModel) {
            index.setSearchModel(SearchModel.VECTOR_MODEL);
            LOGGER.info("Vector model set");
        } else {
            index.setSearchModel(SearchModel.BOOLEAN_MODEL);
            LOGGER.info("Boolean model set");
        }
        return isVectorModel;
    }

    /**
     * Metoda pro uložení indexu a všech článků do souboru.
     *
     * @return zpráva o uložení
     */
    @GetMapping("/save")
    public Message saveIndexToFile() {
        LOGGER.info("GET /save => saveIndexToFile()");
        index.saveInvertedIndexToFile(INDEX_FILE_NAME);
        IOUtils.saveArticlesRepositoryToFile((ArticleRepositoryImpl) articleRepository, ARTICLES_FILE_NAME);
        return new Message(true, "All indexed files stored to file...");
    }

    /**
     * Metoda pro načtení indexu a všech dokumentů ze souboru
     *
     * @return zpráva a načtení
     */
    @GetMapping("/load")
    public Message loadIndexFromFile() {
        LOGGER.info("GET /load => loadIndexFromFile()");
        index = new Index();
        articleRepository = new ArticleRepositoryImpl();
        boolean isLoaded = index.loadInvertedIndexFromFile(INDEX_FILE_NAME);
        if(isLoaded) {
            articleRepository = IOUtils.loadArticleRepositoryFromFile(ARTICLES_FILE_NAME);
            return new Message(true, "All data has been successfully loaded from file");
        } else {
            return new Message(false, "Cannot load data from file");
        }
    }

    /**
     * Metoda pro smazání všech dat v aplikaci
     *
     * @return zpráva o smazání
     */
    @GetMapping("/delete")
    public Message deleteAllData() {
        LOGGER.info("GET /delete => deleteAllData()");
        index = new Index();
        articleRepository = new ArticleRepositoryImpl();
        return new Message(true, "All data has been successfully deleted");
    }
}

