package jp.ac.titech.cs.se.reqchecker.checker.modifiability;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.cabocha.Phrase;
import jp.ac.titech.cs.se.reqchecker.cabocha.Word;
import jp.ac.titech.cs.se.reqchecker.checker.AbstractChapterChecker;
import jp.ac.titech.cs.se.reqchecker.model.Chapter;
import jp.ac.titech.cs.se.reqchecker.model.Requirement;

public class ReferringRelationDetector extends AbstractChapterChecker {

    final List<Pair<Requirement, Word>> result = new ArrayList<>();

    public ReferringRelationDetector(final Chapter chapter) {
        super(chapter);
    }

    @Override
    public boolean check() {
        final Set<String> numbers = new HashSet<>();
        for (final Requirement req : chapter.getRequirements()) {
            if (!req.getNumber().equals("")) {
                numbers.add(req.getNumber());
            }
            for (final Phrase phrase : req.getSentence().getPhrases()) {
                for (final Word word : phrase.getWords()) {
                    if (containsNumber(numbers, word.toString())) {
                        result.add(Pair.of(req, word));
                    }
                }
            }
        }
        return !result.isEmpty();
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        for (final Pair<Requirement, Word> p : result) {
            final Requirement req = p.getLeft();
            final Word w = p.getRight();
            if (req.getNumber().equals("")) {
                out.writeln("要求文<b><a> [%s] </a></b>は 要求項目<b><a href=\"#%s.\"> [%s] </a></b>を参照している可能性があります。<br>", req.getRawSentence(), w, w);
            } else {
                out.writeln("識別番号<b><a href=\"#%s\"> [%s] </a></b>は 要求項目<b><a href=\"#%s.\"> [%s] </a></b>を参照している可能性があります。<br>", req.getNumber(), req.getNumber(), w, w);
            }
        }

        out.writeln("<a href=\"quality/modifiability.html\"><span class=\"quality\" title=\"要求文が互いに依存していないかどうか\">変更可能性c</span></a>に問題がある可能性があります。<br>");
        out.writeln("---<br>");
    }

    private boolean containsNumber(final Set<String> numbers, final String w) {
        final String withDot = w.endsWith(".") ? w : (w + ".");
        final String withoutDot = w.endsWith(".") ? w.substring(0, w.length() - 1) : w;
        return numbers.contains(withDot) || numbers.contains(withoutDot);
    }

}
