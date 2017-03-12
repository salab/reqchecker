package jp.ac.titech.cs.se.reqchecker.checker.ambiguity;

import java.io.IOException;

import org.apache.commons.lang3.tuple.Pair;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.cabocha.Phrase;
import jp.ac.titech.cs.se.reqchecker.cabocha.Sentence;
import jp.ac.titech.cs.se.reqchecker.cabocha.Word;
import jp.ac.titech.cs.se.reqchecker.checker.AbstractSentenceChecker;
import jp.ac.titech.cs.se.reqchecker.util.AdjacentPairs;

public class NoSubjectChecker extends AbstractSentenceChecker {

    protected boolean result = false;

    public NoSubjectChecker(final Sentence sentence) {
        super(sentence);
    }

    @Override
    public boolean check() {
        for (final Phrase phrase : sentence.getPhrases()) {
            if (phrase.contains(Word.HA) || phrase.contains(Word.GA)) {
                for (final Pair<Word, Word> p : AdjacentPairs.of(phrase.getWords())) {
                    final Word l = p.getLeft();
                    final Word r = p.getRight();
                    if (r.equals(Word.HA) || r.equals(Word.GA)) {
                        if (l.isPosOf("名詞") && !l.isDetailedPosOf("副詞可能") && !l.getType().equals("副詞可能")) {
                            return false;
                        }
                    }
                }
            }
        }
        return result = true;
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        out.writeln("<B>主語が省略</B>されている可能性があります。<BR>");
        out.writeln("<a href=\"quality/unambiguity.html\"><SPAN class=\"quality\" title=\"主語や目的語が省略されているか\">非あいまい性b</SPAN></a>に問題がある可能性があります。<BR>");
        out.writeln("-<SPAN class=\"reqSmall\">[%s ]</SPAN><BR>", sentence.getRaw());
        out.writeln("---<BR>");
    }
}
