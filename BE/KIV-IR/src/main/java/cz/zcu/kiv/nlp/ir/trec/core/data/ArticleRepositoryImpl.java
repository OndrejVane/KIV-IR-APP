package cz.zcu.kiv.nlp.ir.trec.core.data;


import cz.zcu.kiv.nlp.ir.trec.core.model.Article;

import java.io.Serializable;
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
public class ArticleRepositoryImpl implements ArticleRepository, Serializable {

    final static long serialVersionUID = -804698021100570495L;

    private HashMap<Integer, Article> articles = new HashMap<>();

    private int currentFreeId = 0;

    @Override
    public int addNewArticle(Article article) {
        int givenId = currentFreeId;
        article.setId(Integer.toString(currentFreeId));
        articles.put(currentFreeId, article);
        currentFreeId++;
        return givenId;
    }

    @Override
    public void addNewArticles(List<Article> articles) {
        for (Article article : articles) {
            addNewArticle(article);
        }
    }

    @Override
    public Article getArticleById(String articleId) {
        return articles.getOrDefault(Integer.parseInt(articleId), null);
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

    @Override
    public Document getArticleAsDocumentById(int id) {
        Article entry = getArticleById(Integer.toString(id));

        DocumentNew document = new DocumentNew();
        document.setId(entry.getId());
        document.setTitle(entry.getTitle());
        document.setText(entry.getContent());
        document.setDate(entry.getDownloadedDate());

        return document;
    }
}
