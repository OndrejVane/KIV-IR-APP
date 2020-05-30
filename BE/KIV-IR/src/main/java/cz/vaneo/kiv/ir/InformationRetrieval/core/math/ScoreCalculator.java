package cz.vaneo.kiv.ir.InformationRetrieval.core.math;


import cz.vaneo.kiv.ir.InformationRetrieval.core.data.Result;
import cz.vaneo.kiv.ir.InformationRetrieval.core.model.IndexItem;

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
