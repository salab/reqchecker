package jp.ac.titech.cs.se.reqchecker;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class HTMLWriter implements Closeable {

    private final Writer out;

    public HTMLWriter(final String filename) throws IOException {
        this(new BufferedWriter(new FileWriter(filename, false)));
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
