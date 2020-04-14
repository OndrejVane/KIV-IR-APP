package cz.vaneo.kiv.ir.InformationRetrieval.model;

public class Article {

    private String text;

    public Article(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
