package jp.ac.titech.cs.se.reqchecker.checker.ambiguity;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.cabocha.Phrase;
import jp.ac.titech.cs.se.reqchecker.cabocha.Sentence;
import jp.ac.titech.cs.se.reqchecker.cabocha.Word;
import jp.ac.titech.cs.se.reqchecker.checker.AbstractSentenceChecker;
import jp.ac.titech.cs.se.reqchecker.util.Utils;

public class DirectiveWordChecker extends AbstractSentenceChecker {

    protected final List<String> result = new ArrayList<>();

    private static final URL DICTIONARY_URL = DirectiveWordChecker.class.getResource("/directiveWord.txt");

    private static final Set<String> DIRECTIVE_WORDS = new HashSet<>(Utils.readLines(DICTIONARY_URL));

    public DirectiveWordChecker(final Sentence sentence) {
        super(sentence);
    }

    @Override
    public boolean check() {
        for (final Phrase phrase : sentence.getPhrases()) {
            for (final Word w : phrase.getWords()) {
                if (DIRECTIVE_WORDS.contains(w.getNormalized())) {
                    result.add(w.getNormalized());
                }
            }
        }
        return !result.isEmpty();
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        out.writeln("<b>指示語が</b>含まれています。<br>");
        out.writeln("<a href=\"quality/unambiguity.html\"><span class=\"quality\" title=\"指示語が含まれているか\">非あいまい性c</span></a>に問題がある可能性があります。<br>");
        final StringBuilder sb = new StringBuilder("-[");
        for (final Phrase phrase : sentence.getPhrases()) {
            for (final Word word : phrase.getWords()) {
                if (result.contains(word.getNormalized())) {
                    sb.append("<span class=\"word\">").append(word).append("</span>");
                } else {
                    sb.append(word);
                }
            }
        }
        sb.append("]<br>");
        out.writeln(sb.toString());
        out.writeln("---<br>");
    }
}
