package cz.vaneo.kiv.ir.InformationRetrieval.utils;


import cz.vaneo.kiv.ir.InformationRetrieval.model.Article;

import java.util.ArrayList;


public interface FileHelper {

    void saveToFile(ArrayList<Article> articles, String fileName);

    ArrayList<Article> readFromFile(String fileName);

}
