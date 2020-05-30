package cz.vaneo.kiv.ir.InformationRetrieval.core.searching;

import cz.vaneo.kiv.ir.InformationRetrieval.controller.Controller;
import cz.vaneo.kiv.ir.InformationRetrieval.core.data.Document;
import cz.vaneo.kiv.ir.InformationRetrieval.core.data.Result;
import cz.vaneo.kiv.ir.InformationRetrieval.core.math.CosineSimilarityCalculator;
import cz.vaneo.kiv.ir.InformationRetrieval.core.math.ScoreCalculator;
import cz.vaneo.kiv.ir.InformationRetrieval.core.model.IndexItem;
import cz.vaneo.kiv.ir.InformationRetrieval.core.preprocessing.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Třída představující invertovaný seznam se všemi zaindexovanými slovy.
 *
 * Dále třída obsahuje metody pro výpočet IDF a TF-IDF
 *
 * @author ondrejvane
 */
public class InvertedList implements Serializable {

    static Logger log = LoggerFactory.getLogger(Controller.class);

    final static long serialVersionUID = -8852692424583653999L;

    /**
     * Hash mapa, která představuje invertovaný seznam:
     * slovo: (documentId, indexItem(počet, score, docId)) -> (documentId, indexItem(počet, score, docId))...
     */
    public HashMap<String, HashMap<String, IndexItem>> invertedList = new HashMap<>();

    /**
     * Hash mapa, která představuje vektor IDF
     */
    private HashMap<String, Double> idf = new HashMap<>();

    /**
     * Hash mapa, kde jsou předpočítány velikosti vektorů TF-IDF každého dokumentu
     */
    private HashMap<String, Double> documentsVectorSizes = new HashMap<>();

    /**
     * Počet zaindexovaných dokumentů
     */
    private int numberOfDocuments = 0;

    /**
     * Instance třídy pro preprocessing jednotlivých dokumentů
     */
    private Preprocessing preprocessing = new BasicPreprocessing(
            new CzechStemmerLight(), new AdvancedTokenizer(), CzechStopWordsLoader.readStopWords("stopwords.txt"), false, true, true);


    /**
     * Metoda pro přidání listu dokumentů do invertovaného seznamu.
     *
     * @param documents list dokumentů
     */
    public void addDocumentsToInvertedList(List<Document> documents) {
        for (Document document : documents) {
            addDocumentToInvertedList(document);
        }
        log.info("Data indexed :" + invertedList.size());
    }

    /**
     * Metoda pro přidání jednoho dkumentu do invertovaného seznamu
     *
     * @param document dokument
     */
    public void addDocumentToInvertedList(Document document){
        numberOfDocuments++;
        preprocessing.index(document.getText());
        Map<String, Integer> newWords = preprocessing.getWordFrequencies();
        for (Map.Entry<String, Integer> entry : newWords.entrySet()) {
            if(invertedList.containsKey(entry.getKey())) {
                invertedList.get(entry.getKey()).put(document.getId(), new IndexItem(document.getId(), entry.getValue()));
            } else {
                HashMap<String, IndexItem> indexItem = new HashMap<>();
                indexItem.put(document.getId(), new IndexItem(document.getId(), entry.getValue()));
                invertedList.put(entry.getKey(), indexItem);
            }
        }
    }

    /**
     * Metoda pro spočtení vektoru IDF
     */
    public void calculateIdf(){
        idf = new HashMap<>();
        log.info("Calculating IDF...");
        for (Map.Entry<String, HashMap<String, IndexItem>> entry : this.invertedList.entrySet()) {
            double temp = (double) this.numberOfDocuments / (double) entry.getValue().size();
            temp = Math.log10(temp);
            if (temp != 0.0) {
                idf.put(entry.getKey(), temp);
            }
        }

        log.info("Calculating IDF successfully finished");
    }

    /**
     * Metoda pro spočtení TF-IDF. Každá nenulová složka vektoru TF-IDF je uložena
     * v {@link IndexItem}.
     */
    public void calculateTfIdf(){
        log.info("Calculating TF-IDF...");
        for (Map.Entry<String, HashMap<String, IndexItem>> entry : this.invertedList.entrySet()) {
            HashMap<String, IndexItem> wordOccurrences = entry.getValue();

            for (Map.Entry<String, IndexItem> doc : wordOccurrences.entrySet()){
                int numberOfOccurrence = doc.getValue().getNumberOfOccurrence();
                if(this.idf.containsKey(entry.getKey())){
                    double idf = this.idf.get(entry.getKey());
                    doc.getValue().setScoreItem((1 + Math.log10(numberOfOccurrence)) * idf);
                }
            }
        }
        log.info("Calculating TF-IDF successfully finished");
    }

    /**
     * Metoda, která předpočítá velikosti TF-IDF vektorů jednotlivých dokumentů.
     * Jinak by se muselo počítat při hledání.
     */
    public void calculateDocumentVectorSize() {
        documentsVectorSizes = new HashMap<>();
        log.info("Calculating document vector sizes...");
        for (Map.Entry<String, HashMap<String, IndexItem>> entry : this.invertedList.entrySet()) {
            for (Map.Entry<String, IndexItem> entry2 : entry.getValue().entrySet()){
                if (this.documentsVectorSizes.containsKey(entry2.getKey())){
                    // to znamená, že už tam nějaká hodnota je a musím jí příčíst
                    double vectorSize = documentsVectorSizes.get(entry2.getKey());
                    documentsVectorSizes.put(entry2.getKey(), vectorSize + (Math.pow(entry2.getValue().getScoreItem(), 2)));
                } else {
                    // to znamená, že tam ještě žádná hodnota pro zadaný dokument není
                    documentsVectorSizes.put(entry2.getKey() , Math.pow(entry2.getValue().getScoreItem(), 2));
                }
            }
        }

        // odmocnit sečtené hodnoty
        for (Map.Entry<String, Double> entry : this.documentsVectorSizes.entrySet()){
            this.documentsVectorSizes.put(entry.getKey(), Math.pow(entry.getValue(), 0.5));
        }
        log.info("Calculating document vector sizes successfully finished");

    }

    /**
     * Metoda, která spočte TF-IDF pro hledaný výraz pomocí boolan modelu
     * @param parsedQuery zpracovaný boolean dotaz
     * @return TF-IDF vektor pro dotaz
     */
    private HashMap<String, Double> calculateTfIdfForQuery(Map<String, Integer> parsedQuery){
        HashMap<String, Double> queryTfIdf = new HashMap<>();
        for(Map.Entry<String, Integer> entry : parsedQuery.entrySet()){
            if(idf.containsKey(entry.getKey())){
                double tfIdf = idf.get(entry.getKey()) * (1 + Math.log10(entry.getValue()));
                if(tfIdf != 0.0) {
                    queryTfIdf.put(entry.getKey(), tfIdf);
                }
            }
        }
        return queryTfIdf;
    }

    /**
     * Metoda pro hledání pomocí vektorového modelu
     *
     * @param query textový dotaz
     * @return list nalezených výsledků se spočtenými váhami
     */
    public List<Result> searchWithVectorModel(String query) {
        Map<String, Integer> parsedQuery =  this.preprocessing.index(query);
        HashMap<String, Double> queryTfIdf = calculateTfIdfForQuery(parsedQuery);

        ScoreCalculator scoreCalculator = new CosineSimilarityCalculator();

        return scoreCalculator.calculateScore(this.invertedList, documentsVectorSizes, queryTfIdf);
    }

    public Preprocessing getPreprocessor() {
        return preprocessing;
    }
}
