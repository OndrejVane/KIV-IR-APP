package cz.vaneo.kiv.ir.InformationRetrieval.core.data;



import cz.vaneo.kiv.ir.InformationRetrieval.core.model.Article;

import java.util.List;

/**
 * Interaface pro ukládání jednotlivých článků.
 *
 * @author ondrejvane
 */
public interface ArticleRepository {

    void addNewArticle(Article article);

    void addNewArticles(List<Article> articles);

    Article getArticleById(String articleId);

    List<Article> getAllArticles();

    void deleteAllArticles();

    void deleteArticleById(String articleId);

    int getNumberOfDocuments();

    List<Document> getArticlesAsDocument();

}
