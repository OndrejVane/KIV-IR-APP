package cz.vaneo.kiv.ir.InformationRetrieval;

import cz.vaneo.kiv.ir.InformationRetrieval.core.data.ArticleRepository;
import cz.vaneo.kiv.ir.InformationRetrieval.core.data.ArticleRepositoryImpl;
import cz.vaneo.kiv.ir.InformationRetrieval.core.data.Result;
import cz.vaneo.kiv.ir.InformationRetrieval.core.model.Article;
import cz.vaneo.kiv.ir.InformationRetrieval.core.model.QueryResult;
import cz.vaneo.kiv.ir.InformationRetrieval.core.searching.Index;
import cz.vaneo.kiv.ir.InformationRetrieval.core.searching.SearchModel;
import cz.vaneo.kiv.ir.InformationRetrieval.core.utils.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SearchingTest {

    //TODO BEFORE RUN TESTS !!!!!!! => delete all stop words in file 'stopwords.txt'

    private static final String TEST_FILE_NAME = "test_2.json";
    private ArticleRepository articleRepository;
    private Index index;

    @Before
    public void setUp() throws Exception {
        articleRepository = new ArticleRepositoryImpl();
        List<Article> articles = IOUtils.readArticlesFromFile(TEST_FILE_NAME);
        articleRepository.addNewArticles(articles);

        // create index and index data
        index = new Index();
        index.index(articleRepository.getArticlesAsDocument());
    }

    @After
    public void tearDown() throws Exception {
        articleRepository.deleteAllArticles();
        articleRepository = null;
        index = null;
    }

    @Test
    public void vectorSearch_CheckScore() {
        index.setSearchModel(SearchModel.VECTOR_MODEL);
        List<Result> results;

        //query 1
        String query1 = "tropical fish sea";
        results = index.search(query1);

        // check number of results
        assertEquals(4, results.size());

        // check score of results and correct order by rank
        assertEquals(0.6613f, results.get(0).getScore(), 0.0001);
        assertEquals(0.2009f, results.get(1).getScore(), 0.0001);
        assertEquals(0.1644f, results.get(2).getScore(), 0.0001);
        assertEquals(0.0125f, results.get(3).getScore(), 0.0001);

        //query 2
        String query2 = "tropical fish";
        results = index.search(query2);

        assertEquals(4, results.size());
        assertEquals(0.3973f, results.get(0).getScore(), 0.0001);
        assertEquals(0.3252f, results.get(1).getScore(), 0.0001);
        assertEquals(0.0247f, results.get(2).getScore(), 0.0001);
        assertEquals(0.0247f, results.get(3).getScore(), 0.0001);

    }

    @Test
    public void booleanSearch_CheckNumberOfResults() {
        index.setSearchModel(SearchModel.BOOLEAN_MODEL);
        List<Result> results;

        //query 1
        String query1 = "NOT fish";
        results = index.search(query1);
        // check number of results
        assertEquals(1, results.size());

        //query 2
        String query2 = "fish";
        results = index.search(query2);
        // check number of results
        assertEquals(4, results.size());

        //query 3
        String query3 = "NOT tropical AND sea";
        results = index.search(query3);
        // check number of results
        assertEquals(1, results.size());

        //query 4
        String query4 = "NOT tropical AND fish";
        results = index.search(query4);
        // check number of results
        assertEquals(2, results.size());

        //query 5
        String query5 = "NOT tropical OR country";
        results = index.search(query5);
        // check number of results
        assertEquals(1, results.size());

        //query 6
        String query6 = "czechia OR country";
        results = index.search(query6);
        // check number of results
        assertEquals(2, results.size());

        //query 7
        String query7 = "NOT (czechia OR country)";
        results = index.search(query7);
        // check number of results
        assertEquals(3, results.size());
    }

    @Test
    public void vectorSearch_LimitedResults() {
        index.setSearchModel(SearchModel.VECTOR_MODEL);
        QueryResult queryResult;

        queryResult = index.search("tropical fish sea", 2);
        assertEquals(2, queryResult.getResults().size());
        assertEquals(4, queryResult.getNumberOfFoundDocuments());

        queryResult = index.search("tropical fish sea", 0);
        assertEquals(0, queryResult.getResults().size());
        assertEquals(4, queryResult.getNumberOfFoundDocuments());
    }

    @Test
    public void booleanSearch_LimitedResults() {
        index.setSearchModel(SearchModel.BOOLEAN_MODEL);
        QueryResult queryResult;

        queryResult = index.search("NOT tropical AND fish", 1);
        assertEquals(1, queryResult.getResults().size());
        assertEquals(2, queryResult.getNumberOfFoundDocuments());

        queryResult = index.search("NOT tropical AND fish", 0);
        assertEquals(0, queryResult.getResults().size());
        assertEquals(2, queryResult.getNumberOfFoundDocuments());

    }


}
