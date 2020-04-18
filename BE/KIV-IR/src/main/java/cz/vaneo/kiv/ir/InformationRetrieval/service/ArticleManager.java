package cz.vaneo.kiv.ir.InformationRetrieval.service;

import cz.vaneo.kiv.ir.InformationRetrieval.model.Article;

import java.util.List;

public interface ArticleManager {

    Article getArticle(int id);

    List<Article> getAllArticles();

    boolean addArticle(Article article);

    boolean deleteAllArticles();

    boolean deleteArticle(int id);
}
