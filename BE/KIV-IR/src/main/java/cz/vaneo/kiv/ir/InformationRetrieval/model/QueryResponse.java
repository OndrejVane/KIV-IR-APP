package cz.vaneo.kiv.ir.InformationRetrieval.model;

import java.util.Date;
import java.util.List;

public class QueryResponse {

    private String expression;
    private Date date;
    private List<Article> articles;

    public QueryResponse(String expression, Date date, List<Article> articles) {
        this.expression = expression;
        this.date = date;
        this.articles = articles;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
