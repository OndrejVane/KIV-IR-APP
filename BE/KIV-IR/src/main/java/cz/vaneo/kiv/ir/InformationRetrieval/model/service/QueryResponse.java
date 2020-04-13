package cz.vaneo.kiv.ir.InformationRetrieval.model.service;

public class QueryResponse {

    private String response;

    public QueryResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
