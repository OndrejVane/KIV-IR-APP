package cz.vaneo.kiv.ir.InformationRetrieval.core.searching;


import cz.vaneo.kiv.ir.InformationRetrieval.core.data.Result;
import cz.vaneo.kiv.ir.InformationRetrieval.core.model.QueryResult;

import java.util.List;

/**
 * Created by Tigi on 6.1.2015.
 *
 * Rozhraní umožňující vyhledávat v zaindexovaných dokumentech.
 *
 * Pokud potřebujete, můžete přidat další metody k implementaci, ale neměňte signaturu
 * již existující metody search.
 *
 * Metodu search implementujte ve tříde {@link Index}
 */
public interface Searcher {
    /**
     * Metoda pro hledání v zaindexovaných dokumentech. Pokud je vyhledáváno
     * pomocí vektorového modelu, tak jsou výsledký seřazeny dle vypočteného skore.
     *
     * @param query textový dotaz
     * @return seznam všech nalezených seřazených dokumentů
     */
    List<Result> search(String query);

    /**
     * Metoda pro hledání v zaindexovaných dokumentech. Tato metoda vrátí pouze
     * zadaný počet dokumentů. Vratí objekt, kde je nejlepších X výsledků a celkový počet výsledků
     *
     * @param query textový dotaz
     * @param numberOfResults počet výsledků, které se mají vráti
     * @return top X nalezených dokumentů a celkový počet nalezených dokumentů
     */
    QueryResult search(String query, int numberOfResults);
}
