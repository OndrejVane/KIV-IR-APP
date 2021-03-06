package cz.zcu.kiv.nlp.ir.trec.core.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import cz.zcu.kiv.nlp.ir.trec.data.ArticleRepositoryImpl;
import cz.zcu.kiv.nlp.ir.trec.core.model.Article;
import cz.zcu.kiv.nlp.ir.trec.core.searching.InvertedList;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author tigi
 *
 * IOUtils, existující metody neměňte.
 */
public class IOUtils {

    /**
     * Read lines from the stream; lines are trimmed and empty lines are ignored.
     *
     * @param inputStream stream
     * @return list of lines
     */
    public static List<String> readLines(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("Cannot locate stream");
        }
        try {
            List<String> result = new ArrayList<String>();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
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

    /**
     * Read lines from the stream; lines are trimmed and empty lines are ignored.
     *
     * @param inputStream stream
     * @return text
     */
    public static String readFile(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        if (inputStream == null) {
            throw new IllegalArgumentException("Cannot locate stream");
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            inputStream.close();

            return sb.toString().trim();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Saves lines from the list into given file; each entry is saved as a new line.
     *
     * @param file file to save
     * @param list lines of text to save
     */
    public static void saveFile(File file, Collection<String> list) {
        PrintStream printStream = null;
        try {
            printStream = new PrintStream(new FileOutputStream(file), true, "UTF-8");

            for (String text : list) {
                printStream.println(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (printStream != null) {
                printStream.close();
            }
        }
    }

    /**
     * Saves lines from the list into given file; each entry is saved as a new line.
     *
     * @param file file to save
     * @param text text to save
     */
    public static void saveFile(File file, String text) {
        PrintStream printStream = null;
        try {
            printStream = new PrintStream(new FileOutputStream(file), true, "UTF-8");

            printStream.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (printStream != null) {
                printStream.close();
            }
        }
    }

    public static ArrayList<Article> readArticlesFromFile(String fileName) {
        Type REVIEW_TYPE = new TypeToken<ArrayList<Article>>() {
        }.getType();
        Gson gson = new Gson();
        JsonReader reader = null;
        ArrayList<Article> data = null;
        try {
            reader = new JsonReader(new FileReader(fileName));
            data = gson.fromJson(reader, REVIEW_TYPE); // contains the whole reviews list
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static InvertedList loadInvertedIndex(String filename) {
        try {
            int BUFFER_STREAM_SIZE = 16 * 1024 * 1024;

            FileInputStream fileInputStream = new FileInputStream(filename);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, BUFFER_STREAM_SIZE);
            ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
            InvertedList invertedList = (InvertedList) objectInputStream.readObject();
            objectInputStream.close();
            return invertedList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveInvertedIndex(InvertedList invertedList, String filename) {
        try {
            int BUFFER_STREAM_SIZE = 16 * 1024 * 1024;

            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, BUFFER_STREAM_SIZE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
            objectOutputStream.writeObject(invertedList);
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArticleRepositoryImpl loadArticleRepositoryFromFile(String filename) {
        try {
            int BUFFER_STREAM_SIZE = 16 * 1024 * 1024;

            FileInputStream fileInputStream = new FileInputStream(filename);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, BUFFER_STREAM_SIZE);
            ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
            ArticleRepositoryImpl articleRepository = (ArticleRepositoryImpl) objectInputStream.readObject();
            objectInputStream.close();
            return articleRepository;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveArticlesRepositoryToFile(ArticleRepositoryImpl articleRepository, String filename) {
        try {
            int BUFFER_STREAM_SIZE = 16 * 1024 * 1024;

            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, BUFFER_STREAM_SIZE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
            objectOutputStream.writeObject(articleRepository);
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
