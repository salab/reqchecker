package jp.ac.titech.cs.se.reqchecker.cabocha;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class CabochaParser {
    private final String cabochaPath;

    /**
     * The constructor.
     * 
     * @param cabochaPath
     *            The executable path of cabocha
     */
    public CabochaParser(final String cabochaPath) {
        this.cabochaPath = cabochaPath;
    }

    /**
     * Parse a string sentence into Sentence object.
     *
     * @param rawSentence
     *            string to be parsed
     * @return a Sentence representation of the given string
     */
    public Sentence parse(final String rawSentence) {
        try {
            final File input = createTemporaryFile(rawSentence);
            final String output = executeCabocha(input);
            input.delete();
            return new Sentence(rawSentence, output);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String executeCabocha(final File input) throws IOException {
        final ProcessBuilder pb = new ProcessBuilder();
        pb.command(cabochaPath, "-f1", "-n1", input.getAbsolutePath());
        final Process proc = pb.start();

        final InputStreamReader r = new InputStreamReader(proc.getInputStream(), Charset.defaultCharset());
        final StringBuilder sb = new StringBuilder();
        try (final BufferedReader reader = new BufferedReader(r)) {
            String buffer;
            while (!(buffer = reader.readLine()).equals("EOS")) {
                sb.append(buffer).append("\n");
            }
        }
        return sb.toString();
    }

    protected File createTemporaryFile(final String sentence) throws IOException {
        final File file = File.createTempFile("reqchecker", ".txt");
        Files.write(file.toPath(), sentence.getBytes());
        return file;
    }
}
