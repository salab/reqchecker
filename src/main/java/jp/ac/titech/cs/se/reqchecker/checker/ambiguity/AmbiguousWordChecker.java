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
import jp.ac.titech.cs.se.reqchecker.wordnet.JawjawUtils;

public class AmbiguousWordChecker extends AbstractSentenceChecker {

    protected final List<String> result = new ArrayList<>();

    private static final URL DICTIONARY_URL = AmbiguousWordChecker.class.getResource("/ambiguousWord.txt");

    private static final Set<String> AMBIGUOUS_WORDS = new HashSet<>(Utils.readLines(DICTIONARY_URL));

    public AmbiguousWordChecker(final Sentence sentence) {
        super(sentence);
    }

    @Override
    public boolean check() {
        for (final Phrase phrase : sentence.getPhrases()) {
            for (final Word w : phrase.getWords()) {
                final Set<String> synonym = JawjawUtils.getSynonyms(w.getNormalized(), "jpn");
                synonym.retainAll(AMBIGUOUS_WORDS);
                if (!synonym.isEmpty()) {
                    result.add(w.getNormalized());
                }
            }
        }
        return !result.isEmpty();
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        out.writeln("<B>あいまいな言葉が</B>含まれています。<BR>");
        out.writeln("<a href=\"quality/unambiguity.html\"><SPAN class=\"quality\" title=\"判断基準があいまいな語句あるいは範囲や境界を表す語句が含まれているか\">非あいまい性dまたはe</SPAN></a>に問題がある可能性があります。<BR>");
        final StringBuilder sb = new StringBuilder("-[");
        for (final Phrase phrase : sentence.getPhrases()) {
            for (final Word word : phrase.getWords()) {
                if (result.contains(word.getNormalized())) {
                    sb.append("<SPAN class=\"word\">").append(word).append("</SPAN>");
                } else {
                    sb.append(word);
                }
            }
        }
        sb.append("]<BR>");
        out.writeln(sb.toString());
        out.writeln("---<BR>");
    }
}
