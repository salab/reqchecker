package jp.ac.titech.cs.se.reqchecker.checker.ambiguity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.cabocha.Phrase;
import jp.ac.titech.cs.se.reqchecker.cabocha.Sentence;
import jp.ac.titech.cs.se.reqchecker.cabocha.Word;
import jp.ac.titech.cs.se.reqchecker.checker.AbstractSentenceChecker;

public class NoObjectChecker extends AbstractSentenceChecker {

    protected final List<Phrase> result = new ArrayList<>();

    public NoObjectChecker(final Sentence sentence) {
        super(sentence);
    }

    @Override
    public boolean check() {
        for (final Phrase phrase : sentence.getPhrases()) {
            if (phrase.isVerb() && !phrase.contains(Word.RERU)) {
                if (phrase.getObjectivePhrases().isEmpty()) {
                    result.add(phrase);
                }
            }
        }
        return !result.isEmpty();
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        out.writeln("<b>目的語が省略</b>されている可能性があります。<br>");
        out.writeln("<a href=\"quality/unambiguity.html\"><span class=\"quality\" title=\"主語や目的語が省略されているか\">非あいまい性b</span></a>に問題がある可能性があります。<br>");
        final StringBuilder sb = new StringBuilder("-[");
        for (final Phrase phrase : sentence.getPhrases()) {
            if (result.contains(phrase)) {
                sb.append(" <span class=\"parallel\">").append(phrase).append("</span>");
            } else {
                sb.append(" ").append(phrase);
            }
        }
        sb.append(" ]<br>");
        out.writeln(sb.toString());
        out.writeln("---<br>");
    }
}
