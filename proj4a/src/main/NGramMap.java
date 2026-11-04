package main;

import java.util.Collection;
import java.util.TreeMap;
import java.util.Map;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

import edu.princeton.cs.algs4.In;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    Map<String, TimeSeries> wordMap;
    TimeSeries yearMap;

    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        In inWord = new In(wordHistoryFilename);
        In inYear = new In(yearHistoryFilename);
        wordMap = new TreeMap<String, TimeSeries>();
        yearMap = new TimeSeries();

        while (!inWord.isEmpty()) {
            String[] splitLine = inWord.readLine().split("\t");
            String word = splitLine[0];
            int year = Integer.parseInt(splitLine[1]);
            Double frequency = Double.parseDouble(splitLine[2]);
            if (wordMap != null && !wordMap.containsKey(word)) {
                wordMap.put(word, new TimeSeries());
            }
            wordMap.get(word).put(year, frequency);
        }
        while (!inYear.isEmpty()) {
            String[] splitLine = inYear.readLine().split(",");
            int year = Integer.parseInt(splitLine[0]);
            Double total = Double.parseDouble(splitLine[1]);
            yearMap.put(year, total);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries ret = new TimeSeries();
        if (wordMap.containsKey(word)) {
            ret = new TimeSeries(wordMap.get(word), startYear, endYear);
        }
        return ret;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        TimeSeries ret = new TimeSeries();
        if (wordMap.containsKey(word)) {
            ret = new TimeSeries(wordMap.get(word), MIN_YEAR, MAX_YEAR);
        }
        return ret;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(this.yearMap, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordHistory = countHistory(word, startYear, endYear);
        TimeSeries totalHistory = totalCountHistory();
        return wordHistory.dividedBy(totalHistory);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries wordHistory = countHistory(word);
        TimeSeries totalHistory = totalCountHistory();
        return wordHistory.dividedBy(totalHistory);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries add = new TimeSeries();
        for (String word : words) {
            add = add.plus(countHistory(word, startYear, endYear));
        }
        return add.dividedBy(totalCountHistory());
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries add = new TimeSeries();
        for (String word : words) {
            add = add.plus(countHistory(word));
        }
        return add.dividedBy(totalCountHistory());
    }

}
