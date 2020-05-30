package cz.vaneo.kiv.ir.InformationRetrieval.core.searching;



import cz.vaneo.kiv.ir.InformationRetrieval.core.math.Intersections;
import cz.vaneo.kiv.ir.InformationRetrieval.core.model.IndexItem;
import org.apache.lucene.search.BooleanClause;

import java.util.*;

public class BooleanQueryFinder {

    private InvertedList invertedList;

    public BooleanQueryFinder(InvertedList invertedList) {
        this.invertedList = invertedList;
    }

    public List<IndexItem> getIndexItemsForQuery(ParsedBooleanQuery parsedQuery) {
        HashMap<String, IndexItem> foundItems = getIndexItemsForQuery(parsedQuery, false);
        List<IndexItem> listFoundItems = new ArrayList<>();
        for (Map.Entry<String, IndexItem> entry : foundItems.entrySet()) {
            listFoundItems.add(entry.getValue());
        }
        return listFoundItems;
    }

    private HashMap<String, IndexItem> getIndexItemsForQuery(ParsedBooleanQuery parsedQuery, boolean notOperator) {
        // node is term
        if (parsedQuery.isTerm()) {
            if (notOperator) {

                // get all unique postings
                HashMap<String, IndexItem> allIndexedItems = getAllUniqueDocuments();

                // remove all postings which are relevant to notTerm
                HashMap<String, IndexItem> toBeRemoved = getDocumentsByTerm(parsedQuery.getTerm());
                for (Map.Entry<String, IndexItem> entry : toBeRemoved.entrySet()) {
                    allIndexedItems.remove(entry.getKey());
                }

                return allIndexedItems;
            } else {
                return getDocumentsByTerm(parsedQuery.getTerm());
            }

        } else {
            Collection<ParsedBooleanQuery> childQuery;
            HashMap<String, IndexItem> res = new HashMap<>();
            for(BooleanClause.Occur occurrence : parsedQuery.getChildrens().keySet()) {
                childQuery = parsedQuery.getChildrens().get(occurrence);
                if(occurrence.equals(BooleanClause.Occur.MUST)) {
                    if(notOperator){
                        for(ParsedBooleanQuery child : childQuery) {
                            res = Intersections.or(res, getIndexItemsForQuery(child, true));
                        }
                    } else {
                        for(ParsedBooleanQuery child : childQuery) {
                            res = Intersections.and(res, getIndexItemsForQuery(child, false));
                        }
                    }
                } else if (occurrence.equals(BooleanClause.Occur.SHOULD)){
                    if(notOperator) {
                        for(ParsedBooleanQuery child : childQuery) {
                            res = Intersections.and(res, getIndexItemsForQuery(child, true));
                        }
                    } else {
                        for(ParsedBooleanQuery child : childQuery) {
                            res = Intersections.or(res, getIndexItemsForQuery(child, false));
                        }
                    }

                } else if (occurrence.equals(BooleanClause.Occur.MUST_NOT)){
                    for(ParsedBooleanQuery child : childQuery) {
                        res = Intersections.not(res, getIndexItemsForQuery(child, true));
                    }
                }
            }
            return res;
        }
    }

    private HashMap<String, IndexItem> getAllUniqueDocuments() {
        HashMap<String, IndexItem> indexedDocuments = new HashMap<>();
        for (Map.Entry<String, HashMap<String, IndexItem>> entry : invertedList.invertedList.entrySet()) {
            indexedDocuments.putAll(entry.getValue());
        }
        return indexedDocuments;
    }

    private HashMap<String, IndexItem> getDocumentsByTerm(String term) {

        if (invertedList.invertedList.containsKey(term)) {
            return invertedList.invertedList.get(term);
        } else {
            return new HashMap<>();
        }
    }
}
