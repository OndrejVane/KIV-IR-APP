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



    @Override
    public void saveToFile(ArrayList<Article> articles, String fileName) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(articles);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Article> readFromFile(String fileName) {
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
}
