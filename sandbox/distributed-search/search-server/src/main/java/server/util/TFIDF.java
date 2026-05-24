package server.util;

import server.model.DocumentStats;

import java.util.*;

public class TFIDF {

    public static double termFrequency(List<String> words, String term) {
        long count = 0;
        for (String word : words) {
            if (term.equalsIgnoreCase(word)) {
                count++;
            }
        }
        return (double) count / words.size();
    }

    public static DocumentStats createDocumentStats(List<String> searchTerms, String document) {
        List<String> words = TextParser.parseWords(document);
        var docStats = new DocumentStats();
        computeTermFrequency(searchTerms, words, docStats);
        return docStats;
    }

    private static void computeTermFrequency(List<String> searchTerms, List<String> words, DocumentStats docStats) {
        for (String term : searchTerms) {
            double termFreq = TFIDF.termFrequency(words, term.toLowerCase());
            docStats.putTermFrequency(term, termFreq);
        }
    }

    private static double inverseDocumentFrequency(String term, Map<String, DocumentStats> docStatsMap) {
        double nt = 0;
        for (String document : docStatsMap.keySet()) {
            var docStats = docStatsMap.get(document);
            double termFrequency = docStats.getFrequency(term);
            if (termFrequency > 0.0) {
                nt++;
            }
        }
        return nt == 0 ? 0 : Math.log10(docStatsMap.size() / nt);
    }

    private static Map<String, Double> termToInverseDocumentFrequencyMap(List<String> terms,
                                                                         Map<String, DocumentStats> docStats) {
        Map<String, Double> termToIDF = new HashMap<>();
        for (String term : terms) {
            double idf = inverseDocumentFrequency(term, docStats);
            termToIDF.put(term, idf);
        }
        return termToIDF;
    }

    private static double documentScore(List<String> terms, DocumentStats docStats, Map<String, Double> termToIdf) {
        double score = 0;
        for (String term : terms) {
            double termFrequency = docStats.getFrequency(term);
            double inverseTermFrequency = termToIdf.get(term);
            score += termFrequency * inverseTermFrequency;
        }
        return score;
    }

    public static Map<Double, List<String>> documentScoreMap(List<String> terms, Map<String, DocumentStats> docStatsMap) {
        TreeMap<Double, List<String>> scoreToDocs = new TreeMap<>();
        Map<String, Double> termToIdf = termToInverseDocumentFrequencyMap(terms, docStatsMap);
        for (String doc : docStatsMap.keySet()) {
            var docStats = docStatsMap.get(doc);
            double score = documentScore(terms, docStats, termToIdf);
            addDocumentScoreToTreeMap(scoreToDocs, score, doc);
        }
        return scoreToDocs.descendingMap();
    }

    private static void addDocumentScoreToTreeMap(TreeMap<Double, List<String>> scoreToDoc, double score, String document) {
        List<String> docsWithCurrentScore = scoreToDoc.getOrDefault(score, new ArrayList<>());
        docsWithCurrentScore.add(document);
        scoreToDoc.put(score, docsWithCurrentScore);
    }

}
