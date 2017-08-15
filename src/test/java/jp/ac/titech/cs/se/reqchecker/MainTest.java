package jp.ac.titech.cs.se.reqchecker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.common.io.PatternFilenameFilter;

public class MainTest {

    private final static String CABOCHA_URL = "/usr/local/bin/cabocha";

    private void check(final String filename, final String filenameExpected) {
        final String result = Main.execute(filename, CABOCHA_URL);
        assertThat(result).isEqualTo(contentOf(new File(filenameExpected)));
    }

    private void update(final String filename, final String filenameExpected) throws IOException {
        Main.execute(filename, CABOCHA_URL, filenameExpected);
    }

    // @Test
    public void testHtml() {
        for (final String f : getSampleFiles()) {
            final String input = "sample/" + f;
            final String output = "output/" + f.replace(".txt", ".html");
            check(input, output);
        }
    }

    @Test
    public void testReserve() {
        checkOne("reserve");
    }

    @Test
    public void testWinery() {
        checkOne("winery");
    }

    @Test
    public void testAirline() {
        checkOne("airline");
    }

    @Test
    public void testTextFormatter() {
        checkOne("textformatter");
    }

    private void checkOne(final String f) {
        final String input = "sample/" + f + ".txt";
        final String output = "output/" + f + ".html";
        check(input, output);
    }

    private String[] getSampleFiles() {
        return new File("sample").list(new PatternFilenameFilter(".*\\.txt"));
    }

    public void updateOracles() throws IOException {
        for (final String f : getSampleFiles()) {
            final String input = "sample/" + f;
            final String output = "output/" + f.replace(".txt", ".html");
            update(input, output);
        }
    }
}
