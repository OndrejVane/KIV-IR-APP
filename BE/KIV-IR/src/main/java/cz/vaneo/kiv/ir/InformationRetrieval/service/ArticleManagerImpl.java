package cz.vaneo.kiv.ir.InformationRetrieval.service;

import cz.vaneo.kiv.ir.InformationRetrieval.model.Article;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleManagerImpl implements ArticleManager {

    private final Map<Integer, Article> articles = new HashMap<>();
    private Integer currentFreeId = 0;

    @Override
    public Article getArticle(int id) {
        if (!this.articles.containsKey(id)) {
            return null;
        }
        return this.articles.get(id);
    }

    @Override
    public boolean addArticle(Article article) {
        article.setId(currentFreeId);
        currentFreeId++;

        if (this.articles.containsKey(article.getId())) {
            return false;
        }

        this.articles.put(article.getId(), article);
        return true;
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> allArticles = new ArrayList<>();
        for (Integer id : articles.keySet()) {
            allArticles.add(articles.get(id));
        }
        return allArticles;
    }

    @Override
    public boolean deleteAllArticles() {
        articles.clear();
        return true;
    }

    @Override
    public boolean deleteArticle(int id) {
        if(articles.containsKey(id)){
            articles.remove(id);
            return true;
        }
        return false;
    }
}
