package cz.vaneo.kiv.ir.InformationRetrieval.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class Utils {

    private static Logger LOGGER = LoggerFactory.getLogger(Utils.class);
    public static final java.text.DateFormat SDF = new SimpleDateFormat("yyyy-MM-dd_HH_mm_SS");

    /**
     * Saves text to given file.
     *
     * @param file file to save
     * @param text text to save
     */
    public static void saveFile(File file, String text) {
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.print(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves lines from the list into given file; each entry is saved as a new line.
     *
     * @param file file to save
     * @param list lines of text to save
     */
    public static void saveFile(File file, Collection<String> list) {
        try {

            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            for (String text : list) {
                printStream.println(text);
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }


    /**
     * Read lines from the stream; lines are trimmed and empty lines are ignored.
     *
     * @param inputStream stream
     * @return list of lines
     */
    public static List<String> readTXTFile(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("Cannot locate stream");
        }
        try {
            List<String> result = new ArrayList<String>();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    result.add(line.trim());
                }
            }

            inputStream.close();

            return result;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String removeLast(String data, String toBeReplaced, String replacement) {
        if (data.endsWith(toBeReplaced)) {
            return data.substring(0, data.length() - 1) + replacement;
        } else {
            return data;
        }
    }

    public static void printWordFrequencies(Map<String, Integer> wordFrequencies) {
        for (String key : wordFrequencies.keySet()) {
            LOGGER.info(key + ": " + wordFrequencies.get(key));
        }
        LOGGER.info("");
        printSortedDictionary(wordFrequencies);
    }

    private static void printSortedDictionary(Map<String, Integer> wordFrequencies) {
        Object[] a = wordFrequencies.keySet().toArray();
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
    }
}
