package searchengine;

import searchengine.model.SearchDataset;
import searchengine.model.SearchEngineContext;
import searchengine.util.SearchEngineUtils;

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