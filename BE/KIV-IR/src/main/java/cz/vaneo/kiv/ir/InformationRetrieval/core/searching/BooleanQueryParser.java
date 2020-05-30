package cz.vaneo.kiv.ir.InformationRetrieval.core.searching;

import cz.vaneo.kiv.ir.InformationRetrieval.core.preprocessing.Preprocessing;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.precedence.PrecedenceQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

/**
 * Třída pro zpracování boolovského dotazu. Parsování probíhá za pomoci
 * knihovny Lucene - query parser.
 *
 * @author ondrejvane
 */
public class BooleanQueryParser {

    /**
     * Instance preprocessingu jednotlivých slov v dotazu
     */
    private Preprocessing preprocessing;

    /**
     * Lucene query parser
     */
    private PrecedenceQueryParser parser = new PrecedenceQueryParser();

    public BooleanQueryParser(Preprocessing preprocessing) {
        this.preprocessing = preprocessing;
    }

    /**
     * Metoda, která zpracuje textový dotaz a vytvoří z neho strom představující
     * dotaz. Strom bere v úvahu závorky.
     *
     * @param rawQuery textový boolovský dotaz
     * @return dotaz uložený ve stromu
     * @throws QueryNodeException výjimka pro špatně formulovaný dotaz
     */
    public ParsedBooleanQuery parseQuery(String rawQuery) throws QueryNodeException {
        Query q = parser.parse(rawQuery, "");
        ParsedBooleanQuery parsedQuery = new ParsedBooleanQuery();
        if (q.getClass() == TermQuery.class){
            parsedQuery.setTerm(true);
            parsedQuery.setTerm(preprocessing.processTerm(((TermQuery)q).getTerm().text()));
        } else if (q.getClass() == BooleanQuery.class) {
            parsedQuery.setTerm(false);
            createQueryTree(q, parsedQuery);
        } else {
            throw  new QueryNodeException(new MessageImpl("Unknown type of query"));
        }

        return parsedQuery;
    }

    /**
     * Pomocná rekurzivní metoda pro vytvoření stromu ze zpracovaného dotazu.
     *
     * @param luceneQuery zpracovaný dotaz
     * @param parsedQuery uzel stromu hledaného výrazu
     */
    private void createQueryTree(Query luceneQuery, ParsedBooleanQuery parsedQuery) {

        BooleanQuery booleanQuery = (BooleanQuery) luceneQuery;

        for(BooleanClause clause : booleanQuery.clauses()) {
            ParsedBooleanQuery child = new ParsedBooleanQuery();
            parsedQuery.addChild(clause.getOccur(), child);

            // if boolean => continue creating the tree
            if(clause.getQuery().getClass() == BooleanQuery.class) {
                child.setTerm(clause.getQuery().toString());
                createQueryTree(clause.getQuery(), child);

                // if term => end recursion here
            } else if(clause.getQuery().getClass() == TermQuery.class){
                child.setTerm(preprocessing.processTerm(clause.getQuery().toString()));
                child.setTerm(true);
            }
        }
    }
}
