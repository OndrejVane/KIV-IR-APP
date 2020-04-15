package cz.vaneo.kiv.ir.InformationRetrieval.preprocessing;

import java.util.Map;

public interface Preprocessing {

    void index(String document);

    String getProcessedForm(String text);

    Map<String, Integer> getWordFrequencies();
}
