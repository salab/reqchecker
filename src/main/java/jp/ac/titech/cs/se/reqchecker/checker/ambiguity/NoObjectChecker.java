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
        out.writeln("<B>目的語が省略</B>されている可能性があります。<BR>");
        out.writeln("<a href=\"quality/unambiguity.html\"><SPAN class=\"quality\" title=\"主語や目的語が省略されているか\">非あいまい性b</SPAN></a>に問題がある可能性があります。<BR>");
        final StringBuilder sb = new StringBuilder("-[");
        for (final Phrase phrase : sentence.getPhrases()) {
            if (result.contains(phrase)) {
                sb.append(" <SPAN class=\"parallel\">").append(phrase).append("</SPAN>");
            } else {
                sb.append(" ").append(phrase);
            }
        }
        sb.append(" ]<BR>");
        out.writeln(sb.toString());
        out.writeln("---<BR>");
    }
}
