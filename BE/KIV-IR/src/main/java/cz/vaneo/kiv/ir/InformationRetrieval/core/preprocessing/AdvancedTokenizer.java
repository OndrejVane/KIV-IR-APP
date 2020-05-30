/**
 * Copyright (c) 2014, Michal Konkol
 * All rights reserved.
 */
package cz.vaneo.kiv.ir.InformationRetrieval.core.preprocessing;


import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michal Konkol
 */
public class AdvancedTokenizer implements Tokenizer, Serializable {

    final static long serialVersionUID = -773048926021100215L;

    public static final String DEFAULT_REGEX = "((http?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])" +    // url
            "|" +                                       // or
            "[0-9]{1,2}[.]\\s[0-9]{1,2}[.]\\s[0-9]{2,4}" +           // datum ex 12. 3. 2020
            "|" +                                       // or
            "[0-9]{1,2}[.]\\s[0-9]{1,2}[.]" +           // datum ex 12. 3.
            "|" +                                       // or
            "[0-9]{1,2}[.][0-9]{1,2}[.][0-9]{4}" +      // datum ex 12.4.2021
            "|" +                                       // or
            "[0-9]{1,2}[.][0-9]{1,2}[.]" +              // datum ex. 12.2.
            "|" +                                       // or
            "[a-zA-Z]*[*][a-zA-Z]*" +                   // slovo s hvězdičkou ex. pr*at
            "|" +                                       // or
            "(\\d+[.,](\\d+)?)" +                       // cislo
            "|" +                                       // or
            "([\\p{L}\\d]+)" +                          // mezera
            "|" +                                       // or
            "(<.*?>)" +                                 // html
            "|" +                                       // or
            "([\\p{Punct}]&&[^*])";                     // tecky a ostaní


    public static String[] tokenize(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);

        ArrayList<String> words = new ArrayList<String>();

        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();

            words.add(text.substring(start, end));
        }

        String[] ws = new String[words.size()];
        ws = words.toArray(ws);

        return ws;
    }

    public static String removeAccents(String text) {
        return text == null ? null : Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    @Override
    public String[] tokenize(String text) {
        return tokenize(text, DEFAULT_REGEX);
    }
}
