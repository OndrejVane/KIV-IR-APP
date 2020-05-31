package cz.zcu.kiv.nlp.ir.trec.core.math;


import cz.zcu.kiv.nlp.ir.trec.core.data.Result;
import cz.zcu.kiv.nlp.ir.trec.core.data.ResultImpl;
import cz.zcu.kiv.nlp.ir.trec.core.model.IndexItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementace rozhraní {@link ScoreCalculator}, ktero počítá skóre relevantních dokumentů.
 * Výpočet skoré je pomocí kosínové podobnosti jednotlivých dokumentů.
 *
 * @author ondrejvane
 */
public class CosineSimilarityCalculator implements ScoreCalculator {
    @Override
    public List<Result> calculateScore(HashMap<String, HashMap<String, IndexItem>> invertedList,
                                       HashMap<String, Double> documentsVectorSizes,
                                       HashMap<String, Double> queryTfIdf) {

        HashMap<String, Double> tempResults = new HashMap<>();
        List<Result> results = new ArrayList<>();

        // calculate numerator of cosine similarity for all found documents
        for (Map.Entry<String, Double> entry : queryTfIdf.entrySet()) {
            if (invertedList.containsKey(entry.getKey())) {
                HashMap<String, IndexItem> foundDocuments = invertedList.get(entry.getKey());

                for (Map.Entry<String, IndexItem> entry2 : foundDocuments.entrySet()) {
                    if (tempResults.containsKey(entry2.getKey())) {
                        double tempScore = tempResults.get(entry2.getKey());
                        double tempSumScore = tempScore + (entry2.getValue().getScoreItem() * entry.getValue());
                        tempResults.put(entry2.getKey(), tempSumScore);
                    } else {
                        double tempScore = entry2.getValue().getScoreItem() * entry.getValue();
                        tempResults.put(entry2.getKey(), tempScore);
                    }
                }
            }
        }

        double queryVectorSize = getVectorSize(queryTfIdf);

        // calculate final cosine similarity fro all found documents
        for (Map.Entry<String, Double> entry : tempResults.entrySet()) {

            double cosineSimilarity = entry.getValue() / (queryVectorSize * documentsVectorSizes.get(entry.getKey()));
            ResultImpl result = new ResultImpl();
            result.setDocumentID(entry.getKey());
            result.setScore((float) cosineSimilarity);
            results.add(result);
        }

        return results;

    }

    private double getVectorSize(HashMap<String, Double> queryTfIdf) {
        double result = 0.0;
        for (Map.Entry<String, Double> entry : queryTfIdf.entrySet()) {
            result = result + Math.pow(entry.getValue(), 2);
        }

        return Math.pow(result, 0.5);

    }
}
