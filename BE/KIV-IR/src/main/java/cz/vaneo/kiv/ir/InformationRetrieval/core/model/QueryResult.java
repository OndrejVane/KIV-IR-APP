package cz.vaneo.kiv.ir.InformationRetrieval.core.model;


import cz.vaneo.kiv.ir.InformationRetrieval.core.data.Result;

import java.util.List;

/**
 * Pomocná modelová třída pro návrat výsledků hledání
 *
 * @author ondrejvane
 */
public class QueryResult {

    /**
     * List výsledků, tentomůže být omezen zadaným
     * počtem hledání ve třídě {@link cz.vaneo.kiv.ir.InformationRetrieval.core.searching.Index})
     */
    private List<Result> results;

    /**
     * Celkový počet nalezených dokumentů
     */
    private int numberOfFoundDocuments;

    public QueryResult() {
    }

    public QueryResult(List<Result> results, int numberOfFoundDocuments) {
        this.results = results;
        this.numberOfFoundDocuments = numberOfFoundDocuments;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public int getNumberOfFoundDocuments() {
        return numberOfFoundDocuments;
    }

    public void setNumberOfFoundDocuments(int numberOfFoundDocuments) {
        this.numberOfFoundDocuments = numberOfFoundDocuments;
    }
}
