package server.util;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.util.Objects;

@UtilityClass
public class FileUtils {

    public static final String[] NO_CONTENT = new String[0];
    public static final String BOOKS_DIRECTORY = "./sandbox/distributed-search/books";

    public static String[] ls(String path) {
        String[] documentNames = new File(path).list();
        if (Objects.isNull(documentNames)) {
            documentNames = NO_CONTENT;
        }
        return documentNames;
    }

}
