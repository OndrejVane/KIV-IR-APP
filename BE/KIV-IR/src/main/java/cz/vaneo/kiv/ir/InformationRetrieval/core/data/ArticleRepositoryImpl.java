package cz.vaneo.kiv.ir.InformationRetrieval.core.data;


import cz.vaneo.kiv.ir.InformationRetrieval.core.model.Article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementace rozhraní {@link ArticleRepository}, která ukládá {@link Article}
 * do hash mapy pro pozdější získání. Články jsou do hashmapy na základě id dokumentu.
 * Id dokumentu se automaticky vygeneruje při přidání nového dokumentu (autoincrement id).
 *
 * @author ondrejvane
 */
public class ArticleRepositoryImpl implements ArticleRepository {

    private HashMap<Integer, Article> articles = new HashMap<>();

    private int currentFreeId = 0;

    @Override
    public void addNewArticle(Article article) {
        article.setId(Integer.toString(currentFreeId));
        articles.put(currentFreeId, article);
        currentFreeId++;
    }

    @Override
    public void addNewArticles(List<Article> articles) {
        for (Article article : articles) {
            addNewArticle(article);
        }
    }

    @Override
    public Article getArticleById(String articleId) {
        return articles.getOrDefault(articleId, null);
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        for (Map.Entry<Integer, Article> entry : this.articles.entrySet()) {
            articles.add(entry.getValue());
        }
        return articles;
    }

    @Override
    public void deleteAllArticles() {
        articles.clear();
    }

    @Override
    public void deleteArticleById(String articleId) {
        articles.remove(Integer.parseInt(articleId));
    }

    @Override
    public int getNumberOfDocuments() {
        return articles.size();
    }

    @Override
    public List<Document> getArticlesAsDocument() {
        List<Document> documents = new ArrayList<>();
        for (Map.Entry<Integer, Article> entry : this.articles.entrySet()) {
            DocumentNew document = new DocumentNew();
            document.setId(entry.getValue().getId());
            document.setTitle(entry.getValue().getTitle());
            document.setText(entry.getValue().getContent());
            document.setDate(entry.getValue().getDownloadedDate());
            documents.add(document);
        }

        return documents;
    }
}
