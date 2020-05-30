package cz.vaneo.kiv.ir.InformationRetrieval.core.searching;

import org.apache.lucene.search.BooleanClause;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Třida představuje uzel stromu, do kterého je zpracován hledaný BOOLEAN výraz.
 */
public class ParsedBooleanQuery {

    /**
     * Zda se jedná o hledané slovo nebo o operand (AND, OR, NOT)
     */
    private boolean isTerm;

    /**
     * Hash mapa, která obsahuje všechny pomotomky tohoto uzlu
     */
    private HashMap<BooleanClause.Occur, List<ParsedBooleanQuery>> children = new HashMap<>();

    /**
     * Pokud je isTerm = true, tak je zde hledané slovo, pokud je isTerm = false, tak je tento
     * string null
     */
    private String term;

    /**
     * Metoda pro přidání pomoka pod tento uzel
     *
     * @param occur oparand (AND, OR, NOT)
     * @param child nový potomek
     */
    public void addChild(BooleanClause.Occur occur, ParsedBooleanQuery child) {
        if (!children.containsKey(occur)) {
            children.put(occur, new ArrayList<>());
        }
        this.children.get(occur).add(child);
    }

    public boolean isTerm() {
        return isTerm;
    }

    public void setTerm(boolean term) {
        isTerm = term;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public HashMap<BooleanClause.Occur, List<ParsedBooleanQuery>> getChildrens() {
        return children;
    }

    public void setChildrens(HashMap<BooleanClause.Occur, List<ParsedBooleanQuery>> childrens) {
        this.children = childrens;
    }

    public List<String> getTerms() {
        return getTermsRec(new ArrayList<>());
    }

    /**
     * Metoda, která projde rekurzivně celý strom a
     * vráti list všech slov v dotazu
     *
     * @param terms slova v dotazu
     * @return všechna slova v dotazu
     */
    private List<String> getTermsRec(List<String> terms) {
        if(isTerm) {
            terms.add(this.term);
        } else {
            for(Map.Entry<BooleanClause.Occur, List<ParsedBooleanQuery>> childOccurrence : this.children.entrySet()) {
                for(ParsedBooleanQuery child : childOccurrence.getValue()) {
                    child.getTermsRec(terms);
                }
            }
        }

        return terms;
    }
}
