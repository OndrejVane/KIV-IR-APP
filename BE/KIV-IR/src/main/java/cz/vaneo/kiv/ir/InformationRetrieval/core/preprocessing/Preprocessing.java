package cz.vaneo.kiv.ir.InformationRetrieval.core.preprocessing;

import java.util.Map;

/**
 * Created by tigi on 29.2.2016.
 */
public interface Preprocessing {

    Map<String, Integer> index(String document);

    String getProcessedForm(String text);

    Map<String, Integer> getWordFrequencies();

    String processTerm(String term);

    void setTestingMode(boolean isTesting);
}
