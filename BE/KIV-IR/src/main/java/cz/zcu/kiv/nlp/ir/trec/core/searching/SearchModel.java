package cz.zcu.kiv.nlp.ir.trec.core.searching;

/**
 * Enum, který představuje v jakém module se bude
 * vyhledávát. Tento enum se nastavuje do {@link Index} před vyhledáváním.
 * Defaultní model pro vyhledávání je VECTOR_MODEL.
 *
 * VECTOR_MODEL - vyhledávání pomocí vektorového modelu a vypočítává se skore
 * BOOLEAN_MODEL - vyhledávání pomocí operandů AND, OR a NOT skoré se nepočítá
 */
public enum SearchModel {
    VECTOR_MODEL,
    BOOLEAN_MODEL
}
