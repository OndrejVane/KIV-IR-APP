package cz.zcu.kiv.nlp.ir.trec.core.model;

import java.io.Serializable;

/**
 * Modelová třída pro jednoduší ukládání informací do invertovaného
 * seznamu.
 *
 * @author ondrejvane
 */
public class IndexItem implements Serializable {

    final static long serialVersionUID = -5209215437542393909L;

    /**
     * Id dokumentu
     */
    private String documentId;

    /**
     * Počet výskytů slova v daném dokumentu
     */
    private int numberOfOccurrence;

    /**
     * Skóre představující TF-IDF váhu daného termu.
     */
    private double scoreItem;   // score představuje jednu složku TF-IDF danného dokumentu

    public IndexItem(String documentId, int numberOfOccurrence) {
        this.documentId = documentId;
        this.numberOfOccurrence = numberOfOccurrence;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getNumberOfOccurrence() {
        return numberOfOccurrence;
    }

    public void setNumberOfOccurrence(int numberOfOccurrence) {
        this.numberOfOccurrence = numberOfOccurrence;
    }

    public double getScoreItem() {
        return scoreItem;
    }

    public void setScoreItem(double scoreItem) {
        this.scoreItem = scoreItem;
    }
}
