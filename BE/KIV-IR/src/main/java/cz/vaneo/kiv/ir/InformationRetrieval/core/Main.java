package cz.vaneo.kiv.ir.InformationRetrieval.core;


import cz.vaneo.kiv.ir.InformationRetrieval.core.data.ArticleRepository;
import cz.vaneo.kiv.ir.InformationRetrieval.core.data.ArticleRepositoryImpl;
import cz.vaneo.kiv.ir.InformationRetrieval.core.data.Result;
import cz.vaneo.kiv.ir.InformationRetrieval.core.model.Article;
import cz.vaneo.kiv.ir.InformationRetrieval.core.searching.Index;
import cz.vaneo.kiv.ir.InformationRetrieval.core.searching.SearchModel;
import cz.vaneo.kiv.ir.InformationRetrieval.core.utils.IOUtils;

import java.util.List;


public class Main {

    static final String OUTPUT_DIR = "./TREC";
    private static final String FILE_NAME = "test_2.json";
    private static ArticleRepository articleRepository = new ArticleRepositoryImpl();
    private static Index index = new Index();

    public static void main(String args[]) {


        List<Article> articles = IOUtils.readArticlesFromFile(FILE_NAME);

        articleRepository.addNewArticles(articles);

        index.index(articleRepository.getArticlesAsDocument());

        search("tropical fish sea");

        search("tropical fish");

        index.setSearchModel(SearchModel.BOOLEAN_MODEL);

        search("NOT fish");

        search("fish");

        search("NOT tropical AND sea");

        search("NOT tropical AND fish");

        search("NOT tropical OR country");

        search("czechia OR country");

        search("NOT (czechia OR country)");


    }

    private static void search(String query) {
        List<Result> results = index.search(query);
        System.out.println("Query: " + query);
        for (Result result : results) {
            System.out.println("DocId: " + result.getDocumentID() + " Score: " + result.getScore() + " Rank: " + result.getRank());
        }
        System.out.println();
    }
}
