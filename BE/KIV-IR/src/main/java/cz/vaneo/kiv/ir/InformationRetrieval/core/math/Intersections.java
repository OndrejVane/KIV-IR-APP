package cz.vaneo.kiv.ir.InformationRetrieval.core.math;



import cz.vaneo.kiv.ir.InformationRetrieval.core.model.IndexItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Třída, která obsahuje statické metody pro množinové operace nad
 * dokumenty (průnik, sjednocení a doplněk).
 *
 * @author ondrejvane
 */
public class Intersections {

    /**
     * Metoda, která udělá průnik dvou množin dokumentů.
     *
     * @param map1 množina dokumentů A
     * @param map2 množina dokumentů B
     * @return průnik množiny dokumentů A a B
     */
    public static HashMap<String, IndexItem> and(HashMap<String, IndexItem> map1, HashMap<String, IndexItem> map2){
        HashMap<String, IndexItem> result = checkEmptyHashMaps(map1, map2);
        if(result != null){
            return result;
        }

        result = new HashMap<>();

        for (Map.Entry<String, IndexItem> entry : map1.entrySet()) {
            if(map2.containsKey(entry.getKey())){
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }

    /**
     * Metoda, která udělá sjednocení dvou množin dokumentů.
     *
     * @param map1 množina dokumentů A
     * @param map2 množina dokumentů B
     * @return sjednocené dokumenty A a B bez duplicit
     */
    public static HashMap<String, IndexItem> or(HashMap<String, IndexItem> map1, HashMap<String, IndexItem> map2){
        HashMap<String, IndexItem> result = checkEmptyHashMaps(map1, map2);
        if(result != null){
            return result;
        }

        result = map1;

        for (Map.Entry<String, IndexItem> entry : map2.entrySet()) {
            if(!result.containsKey(entry.getKey())){
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }

    /**
     * Metoda, která udělá doplněk jedné množiny k druhé.
     *
     * @param map1 množina dokumentů A
     * @param map2 množina dokumentů B
     * @return výsledná doplněk množiny A a B
     */
    public static HashMap<String, IndexItem> not(HashMap<String, IndexItem> map1, HashMap<String, IndexItem> map2){
        HashMap<String, IndexItem> result = checkEmptyHashMaps(map1, map2);
        if(result != null){
            return result;
        }

        result = new HashMap<>();
        for(Map.Entry<String, IndexItem> entry : map2.entrySet()) {
            if (map1.containsKey(entry.getKey())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return and(map1, map2);
    }

    /**
     * Metoda, ktrá kontroluje, zda není některá z map prázdná. Pokud vrátí
     * metoda null, tak to znamená, že žádná mapa není prázdná.
     *
     * @param map1 množina dokumentů A
     * @param map2 množina dokumentů B
     * @return prádná hash mapa | null
     */
    private static HashMap<String, IndexItem> checkEmptyHashMaps(HashMap<String, IndexItem> map1, HashMap<String, IndexItem> map2){
        if(map1.isEmpty() && map2.isEmpty()){
            return new HashMap<>();
        } else if (map1.isEmpty()){
            return map2;
        } else if (map2.isEmpty()){
            return map1;
        }
        return null;
    }
}
