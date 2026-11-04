package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap wordMap;

    public HistoryTextHandler(NGramMap map) {
        wordMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder response = new StringBuilder();
        for (String word : words) {
            response.append(word);
            response.append(": ");
            TimeSeries wordHistory = wordMap.weightHistory(word, startYear, endYear);
            response.append(wordHistory.toString());
            response.append("\n");
        }

        return response.toString();
    }
}
