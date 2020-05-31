package cz.zcu.kiv.nlp.ir.trec.core.searching;


import cz.zcu.kiv.nlp.ir.trec.data.Document;

import java.util.List;

/**
 * Created by Tigi on 6.1.2015.
 *
 * Rozhraní, pro indexaci dokumentů.
 *
 * Pokud potřebujete/chcete můžete přidat další metody např. pro indexaci po jednotlivých dokumentech
 * a jiné potřebné metody (např. CRUD operace update, delete, ... dokumentů), ale zachovejte původní metodu.
 *
 * metodu index implementujte ve třídě {@link Index}
 */
public interface Indexer {

    /**
     * Metoda zaindexuje zadaný seznam dokumentů
     *
     * @param documents list dokumentů
     */
    void index(List<Document> documents);

    /**
     * Metoda zaindexuje daný dokument
     * @param document dokument
     */
    void index(Document document);

    /**
     * Metoda pro načtení invertovaného seznamu ze souboru
     *
     * @param fileName název souboru
     * @return logická hodnota zda načtení proběhlo správně
     */
    boolean loadInvertedIndexFromFile(String fileName);

    /**
     * Metoda pro uložení invertovaného seznamu do souboru
     *
     * @param fileName název souboru
     */
    void saveInvertedIndexToFile(String fileName);
}
