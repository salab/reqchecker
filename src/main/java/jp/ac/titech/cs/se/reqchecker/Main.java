package jp.ac.titech.cs.se.reqchecker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import jp.ac.titech.cs.se.reqchecker.cabocha.CabochaParser;
import jp.ac.titech.cs.se.reqchecker.checker.DocumentChecker;
import jp.ac.titech.cs.se.reqchecker.model.Document;

public class Main {
    public static void main(final String[] args) throws IOException {
        execute(args[0], args[1], args[2]);
    }

    public static String execute(final String inputFilename, final String cabochaPath) {
        final StringWriter writer = new StringWriter();
        execute(inputFilename, cabochaPath, writer);
        return writer.toString();
    }

    public static void execute(final String inputPath, final String cabochaPath, final String outputPath) throws IOException {
        execute(inputPath, cabochaPath, new OutputStreamWriter(Files.newOutputStream(Paths.get(outputPath)), StandardCharsets.UTF_8));
    }

    protected static void execute(final String inputPath, final String cabochaPath, final Writer output) {
        final Document doc = new Document(inputPath, new CabochaParser(cabochaPath));
        final DocumentChecker checker = new DocumentChecker(doc);
        checker.check();

        try (final HTMLWriter writer = new HTMLWriter(output)) {
            checker.render(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
