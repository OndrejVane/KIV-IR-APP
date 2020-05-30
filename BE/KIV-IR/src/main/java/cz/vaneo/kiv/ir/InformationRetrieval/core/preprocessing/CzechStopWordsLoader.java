package cz.vaneo.kiv.ir.InformationRetrieval.core.preprocessing;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class CzechStopWordsLoader implements Serializable {

    final static long serialVersionUID = 1840873192877621653L;

    public static Set<String> readStopWords(String filePath) {
        Set<String> stopWords = new HashSet<>();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(stopWords::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stopWords;
    }

}
