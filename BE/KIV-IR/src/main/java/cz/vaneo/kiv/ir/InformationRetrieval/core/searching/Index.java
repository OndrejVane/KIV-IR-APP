package cz.vaneo.kiv.ir.InformationRetrieval.core.searching;


import cz.vaneo.kiv.ir.InformationRetrieval.controller.Controller;
import cz.vaneo.kiv.ir.InformationRetrieval.core.data.Document;
import cz.vaneo.kiv.ir.InformationRetrieval.core.data.Result;
import cz.vaneo.kiv.ir.InformationRetrieval.core.data.ResultImpl;
import cz.vaneo.kiv.ir.InformationRetrieval.core.model.IndexItem;
import cz.vaneo.kiv.ir.InformationRetrieval.core.model.QueryResult;
import cz.vaneo.kiv.ir.InformationRetrieval.core.preprocessing.Preprocessing;
import cz.vaneo.kiv.ir.InformationRetrieval.core.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;

import java.util.ArrayList;
import java.util.List;


/**
 * @author tigi
 *
 * Třída reprezentující index.
 *
 * Tuto třídu doplňte tak aby implementovala rozhranní {@link Indexer} a {@link Searcher}.
 * Pokud potřebujete, přidejte další rozhraní, která tato třída implementujte nebo
 * přidejte metody do rozhraní {@link Indexer} a {@link Searcher}.
 *
 *
 */
public class Index implements Indexer, Searcher {
    Logger log = LoggerFactory.getLogger(Controller.class);

    private InvertedList invertedList = new InvertedList();

    private SearchModel searchModel = SearchModel.VECTOR_MODEL;

    @Override
    public void index(List<Document> documents) {
        long startTime = System.nanoTime();
        invertedList.addDocumentsToInvertedList(documents);
        invertedList.calculateIdf();
        invertedList.calculateTfIdf();
        invertedList.calculateDocumentVectorSize();
        log.info("Time: " + (System.nanoTime() - startTime)/1000000000);
    }

    @Override
    public void index(Document document) {
        invertedList.addDocumentToInvertedList(document);
        invertedList.calculateIdf();
        invertedList.calculateTfIdf();
        invertedList.calculateDocumentVectorSize();
    }

    @Override
    public boolean loadInvertedIndexFromFile(String fileName) {
        InvertedList invertedList = IOUtils.loadInvertedIndex(fileName);

        if(invertedList == null) {
            this.invertedList = null;
            return false;
        } else {
            this.invertedList = invertedList;
            this.invertedList.calculateIdf();
            this.invertedList.calculateTfIdf();
            this.invertedList.calculateDocumentVectorSize();
            return true;
        }
    }

    @Override
    public void saveInvertedIndexToFile(String fileName) {
        IOUtils.saveInvertedIndex(this.invertedList, fileName);
    }

    public List<Result> search(String query) {
        List<Result> results;
        switch (this.searchModel){
            case VECTOR_MODEL:

                results = this.invertedList.searchWithVectorModel(query);

                // sort all result by calculated score
                results.sort((o2, o1) -> Float.compare(o1.getScore(), o2.getScore()));

                // give all result rank
                int rank = 1;
                for(Result result : results) {
                    ((ResultImpl)result).setRank(rank);
                    rank++;
                }

                log.info("Number of hits is: " + results.size());


                break;
            case BOOLEAN_MODEL:
                ParsedBooleanQuery parsedQuery = null;
                Preprocessing preprocessor = this.invertedList.getPreprocessor();
                try {
                    parsedQuery = new BooleanQueryParser(preprocessor).parseQuery(query);
                } catch (QueryNodeException e) {
                    return null;
                }
                BooleanQueryFinder booleanQueryFinder = new BooleanQueryFinder(this.invertedList);
                List<IndexItem> foundItems = booleanQueryFinder.getIndexItemsForQuery(parsedQuery);

                // transform items to results
                results = new ArrayList<>();
                for (IndexItem indexItem : foundItems) {
                    ResultImpl result = new ResultImpl();
                    result.setDocumentID(indexItem.getDocumentId());
                    result.setScore(0.0f);
                    result.setRank(-1);
                    results.add(result);
                }
                break;
            default:
                results = null;
        }
        return results;
    }

    @Override
    public QueryResult search(String query, int numberOfResults) {
        List<Result> results = search(query);
        List<Result> limitedResults = new ArrayList<>();

        int count = 0;
        for (Result result : results) {
            if(count >= numberOfResults) {
                break;
            }
            limitedResults.add(result);
            count++;
        }

        return new QueryResult(limitedResults, results.size());

    }

    public void setSearchModel(SearchModel searchModel) {
        this.searchModel = searchModel;
    }
}
