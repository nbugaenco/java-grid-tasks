package com.nbugaenco.searchengine;

import com.nbugaenco.searchengine.model.SearchDataset;
import com.nbugaenco.searchengine.model.SearchEngineContext;
import com.nbugaenco.searchengine.util.SearchEngineUtils;

public class Main {

    public static void main(String[] args) {
        SearchDataset dataset = new SearchDataset(SearchEngineUtils.readFile(args));
        SearchEngineContext context = new SearchEngineContext(dataset);

        byte choice;
        do {
            choice = SearchEngineUtils.askUser();
            SearchEngineUtils.processMenuItem(context, choice);
        } while (choice != 0);
    }
}
