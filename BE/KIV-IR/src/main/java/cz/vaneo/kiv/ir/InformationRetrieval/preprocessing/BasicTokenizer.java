package cz.vaneo.kiv.ir.InformationRetrieval.preprocessing;

public class BasicTokenizer implements Tokenizer {

    String splitRegex;

    public BasicTokenizer(String splitRegex) {
        this.splitRegex = splitRegex;
    }

    @Override
    public String[] tokenize(String text) {
        return text.split(splitRegex);
    }
}
