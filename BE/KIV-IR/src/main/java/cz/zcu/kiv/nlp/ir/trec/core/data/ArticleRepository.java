package cz.zcu.kiv.nlp.ir.trec.core.data;



import cz.zcu.kiv.nlp.ir.trec.core.model.Article;

import java.util.List;

/**
 * Interaface pro ukládání jednotlivých článků.
 *
 * @author ondrejvane
 */
public interface ArticleRepository {

    int addNewArticle(Article article);

    void addNewArticles(List<Article> articles);

    Article getArticleById(String articleId);

    List<Article> getAllArticles();

    void deleteAllArticles();

    void deleteArticleById(String articleId);

    int getNumberOfDocuments();

    List<Document> getArticlesAsDocument();

    Document getArticleAsDocumentById(int id);

}
