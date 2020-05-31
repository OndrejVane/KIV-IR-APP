package cz.zcu.kiv.nlp.ir.trec.api_model;

import java.util.List;

public class QueryResponse {

    private List<Article> articles;
    private int numberOfAllResults;
    private String expression;

    public QueryResponse(List<Article> articles, int numberOfAllResults, String expression) {
        this.articles = articles;
        this.numberOfAllResults = numberOfAllResults;
        this.expression = expression;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getNumberOfAllResults() {
        return numberOfAllResults;
    }

    public void setNumberOfAllResults(int numberOfAllResults) {
        this.numberOfAllResults = numberOfAllResults;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
