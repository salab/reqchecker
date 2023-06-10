package jp.ac.titech.cs.se.reqchecker.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import com.google.common.io.Resources;

public class Utils {
    private Utils() {
    }

    public static List<String> readLines(final String filename) {
        try {
            final File file = new File(filename);
            return Files.readAllLines(file.toPath(), Charset.defaultCharset());
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> readLines(final URL url) {
        try {
            return Resources.readLines(url, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T last(final List<T> list) {
        return list.get(list.size() - 1);
    }
}
