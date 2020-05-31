package cz.zcu.kiv.nlp.ir.trec.core.preprocessing;



import cz.zcu.kiv.nlp.ir.trec.core.utils.Utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Tigi on 29.2.2016.
 */
public class BasicPreprocessing implements Preprocessing, Serializable {

    final static long serialVersionUID = -5594049038181240843L;

    Map<String, Integer> wordFrequencies = new HashMap<String, Integer>();
    Stemmer stemmer;
    Tokenizer tokenizer;
    Set<String> stopWords;
    boolean removeAccentsBeforeStemming;
    boolean removeAccentsAfterStemming;
    boolean toLowercase;
    boolean isTestingMode = false;

    final Pattern HTML_PATTERN = Pattern.compile("(([<]|[</])[a-zA-Z0-9]*[>])");
    final String withoutDiacritics = "aAcCCcdDeEeEiInNoOrRsStTuUuUyYzZ";
    final String withDiacritics = "áÁčČĆćďĎéÉěĚíÍňŇóÓřŘšŠťŤúÚůŮýÝžŽ";

    public BasicPreprocessing(Stemmer stemmer, Tokenizer tokenizer, Set<String> stopWords, boolean removeAccentsBeforeStemming, boolean removeAccentsAfterStemming, boolean toLowercase) {
        this.stemmer = stemmer;
        this.tokenizer = tokenizer;
        this.stopWords = stopWords;
        this.removeAccentsBeforeStemming = removeAccentsBeforeStemming;
        this.removeAccentsAfterStemming = removeAccentsAfterStemming;
        this.toLowercase = toLowercase;
    }

    @Override
    public Map<String, Integer> index(String document) {
        if(!isTestingMode) {
            resetPreprocessing();
        }
        if (toLowercase) {
            document = document.toLowerCase();
        }
        if (removeAccentsBeforeStemming) {
            document = removeAccents(document);
        }
        for (String token : tokenizer.tokenize(document)) {

            // remove stop words
            if (stopWords.contains(token)) continue;

            if (stemmer != null) {
                token = stemmer.stem(token);
            }
            if (removeAccentsAfterStemming) {
                token = removeAccents(token);
            }

            token = Utils.removeLast(token, ",", "");
            token = token.replaceAll(" ", "");

            if (!wordFrequencies.containsKey(token)) {
                wordFrequencies.put(token, 0);
            }

            wordFrequencies.put(token, wordFrequencies.get(token) + 1);
        }
        return wordFrequencies;
    }

    @Override
    public String getProcessedForm(String text) {
        if (toLowercase) {
            text = text.toLowerCase();
        }
        if (removeAccentsBeforeStemming) {
            text = removeAccents(text);
        }
        if (stemmer != null) {
            text = stemmer.stem(text);
        }
        if (removeAccentsAfterStemming) {
            text = removeAccents(text);
        }
        return text;
    }

    private String removeAccents(String text) {
        for (int i = 0; i < withDiacritics.length(); i++) {
            text = text.replaceAll("" + withDiacritics.charAt(i), "" + withoutDiacritics.charAt(i));
        }
        return text;
    }

    public Map<String, Integer> getWordFrequencies() {
        return wordFrequencies;
    }

    private void resetPreprocessing() {
        this.wordFrequencies.clear();
    }

    public String processTerm(String term) {
        String t = term.toLowerCase();
        t = removeAccents(t);
        return stemmer.stem(t);
    }

    public void setTestingMode(boolean testing) {
        isTestingMode = testing;
    }
}
