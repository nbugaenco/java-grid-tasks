package searchengine;

import searchengine.model.SearchDataset;
import searchengine.util.SearchEngineUtils;

public class Main {

    public static void main(String[] args) {
        SearchDataset dataset = new SearchDataset(SearchEngineUtils.readFile(args));

        byte choice;
        do {
            choice = SearchEngineUtils.askUser();
            SearchEngineUtils.processMenuItem(dataset, choice);
        } while (choice != 0);
    }
}