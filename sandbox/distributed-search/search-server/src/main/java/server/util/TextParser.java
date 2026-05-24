package server.util;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class TextParser {

    public static List<String> readDocumentPaths(String directoryPath) {
        String[] documentNames = FileUtils.ls(directoryPath);
        return Arrays.stream(documentNames)
                .map(documentName -> directoryPath + "/" + documentName)
                .toList();
    }

    public static List<String> parseWords(String document) {
        List<String> lines = TextParser.parseLines(document);
        List<String> words = new ArrayList<>();
        for (String line : lines) {
            words.addAll(parseWordsFromLine(line));
        }
        return words;
    }

    public static List<String> parseLines(String document) {
        FileReader fileReader;
        try {
            fileReader = new FileReader(document);
        } catch (FileNotFoundException e) {
            return Collections.emptyList();
        }
        var bufferedReader = new BufferedReader(fileReader);
        return bufferedReader.lines().toList();
    }

    public static List<String> parseWordsFromLine(String line) {
        return Arrays.asList(line.split("(\\.)+|(,)+|( )+|(-)+|(\\?)+|(!)+|(;)+|(:)+|(/d)+|(/n)+"));
    }

}
