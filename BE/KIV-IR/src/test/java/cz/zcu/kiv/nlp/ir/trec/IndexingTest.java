package cz.zcu.kiv.nlp.ir.trec;


import cz.zcu.kiv.nlp.ir.trec.core.data.ArticleRepository;
import cz.zcu.kiv.nlp.ir.trec.core.data.ArticleRepositoryImpl;
import cz.zcu.kiv.nlp.ir.trec.core.data.DocumentNew;
import cz.zcu.kiv.nlp.ir.trec.core.data.Result;
import cz.zcu.kiv.nlp.ir.trec.core.model.Article;
import cz.zcu.kiv.nlp.ir.trec.core.searching.Index;
import cz.zcu.kiv.nlp.ir.trec.core.searching.SearchModel;
import cz.zcu.kiv.nlp.ir.trec.core.utils.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IndexingTest {

    //TODO BEFORE RUN TESTS !!!!!!! => delete all stop words in file 'stopwords.txt'

    private static final String TEST_FILE_NAME = "test_3.json";
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
    public void indexing_AddNewDocument() {
        index.setSearchModel(SearchModel.VECTOR_MODEL);
        // create new document
        DocumentNew documentNew = new DocumentNew();
        documentNew.setDate(new Date());
        documentNew.setId("4");
        documentNew.setText("Czechia is a country");
        documentNew.setTitle("");

        // index new document
        index.index(documentNew);

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
}
