package jp.ac.titech.cs.se.reqchecker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HTMLWriter implements Closeable {

    private final Writer out;

    public HTMLWriter(final String filename) throws IOException {
        this(new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(filename)), StandardCharsets.UTF_8)));
    }

    public HTMLWriter(final Writer out) {
        this.out = out;
    }

    public void write(final String line) throws IOException {
        out.write(line);
    }

    public void writeln(final String line) throws IOException {
        out.write(line + "\n");
    }

    public void writeln(final String format, final Object... args) throws IOException {
        writeln(String.format(format, args));
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
