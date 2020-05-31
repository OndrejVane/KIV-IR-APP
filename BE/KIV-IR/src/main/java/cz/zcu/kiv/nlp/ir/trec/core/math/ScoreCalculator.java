package cz.zcu.kiv.nlp.ir.trec.core.math;


import cz.zcu.kiv.nlp.ir.trec.core.data.Result;
import cz.zcu.kiv.nlp.ir.trec.core.model.IndexItem;

import java.util.HashMap;
import java.util.List;

/**
 * Rozhraní pro výpočet skóre dokumentu.
 *
 * @author ondrejvane
 */
public interface ScoreCalculator {

    List<Result> calculateScore(HashMap<String, HashMap<String, IndexItem>> invertedList,
                                HashMap<String, Double> documentsVectorSizes,
                                HashMap<String, Double> queryTfIdf);
}
