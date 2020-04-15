package cz.vaneo.kiv.ir.InformationRetrieval.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import cz.vaneo.kiv.ir.InformationRetrieval.model.Article;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FileHelperJson implements FileHelper {

    private static final String FILE_NAME = "articles.json";

    @Override
    public void saveToFile(ArrayList<Article> articles) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(articles);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));

            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Article> readFromFile() {
        Type REVIEW_TYPE = new TypeToken<ArrayList<Article>>() {
        }.getType();
        Gson gson = new Gson();
        JsonReader reader = null;
        ArrayList<Article> data = null;
        try {
            reader = new JsonReader(new FileReader(FILE_NAME));
            data = gson.fromJson(reader, REVIEW_TYPE); // contains the whole reviews list
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}
