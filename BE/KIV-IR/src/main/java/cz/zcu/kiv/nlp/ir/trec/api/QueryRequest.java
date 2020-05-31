package cz.zcu.kiv.nlp.ir.trec.api;

import java.util.Date;

public class QueryRequest {

    private String query;
    private Date date;

    public QueryRequest(String query, Date date) {
        this.query = query;
        this.date = date;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
