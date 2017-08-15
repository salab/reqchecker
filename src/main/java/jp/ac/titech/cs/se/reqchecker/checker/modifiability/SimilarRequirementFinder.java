package jp.ac.titech.cs.se.reqchecker.checker.modifiability;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import jp.ac.titech.cs.se.reqchecker.checker.AbstractChapterChecker;
import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.model.Chapter;
import jp.ac.titech.cs.se.reqchecker.model.Requirement;
import jp.ac.titech.cs.se.reqchecker.util.AllPairs;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimilarRequirementFinder extends AbstractChapterChecker {

    private final List<Pair<Requirement, Requirement>> result = new ArrayList<>();

    public SimilarRequirementFinder(final Chapter chapter) {
        super(chapter);
    }

    @Override
    public boolean check() {
        for (final Pair<Requirement, Requirement> p : AllPairs.of(chapter.getRequirements())) {
            final Requirement req1 = p.getLeft();
            final Requirement req2 = p.getRight();
            if (req1.getType().isStructural() || req2.getType().isStructural()) {
                continue;
            }
            final int similarity = similarity(req1, req2);
            if (similarity > 50) {
                log.debug("similarity = {} ([{}], [{}])", similarity, req1.getRawSentence(), req2.getRawSentence());
                result.add(p);
            }
        }
        return !result.isEmpty();
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        for (final Pair<Requirement, Requirement> p : result) {
            final Requirement req1 = p.getLeft();
            final Requirement req2 = p.getRight();
            if (!req1.getNumber().equals("") && !req2.getNumber().equals("")) {
                out.writeln("識別番号<B><a href=\"#%s\"> [%s] </a>と<a href=\"#%s\"> [%s] </a>は類似しています</B><BR>", req1.getNumber(), req1.getNumber(), req2.getNumber(), req2.getNumber());
            }
            out.writeln("[%s]<BR>", req1.getRawSentence());
            out.writeln("[%s]<BR>", req2.getRawSentence());
            out.writeln("<a href=\"quality/modifiability.html\"><SPAN class=\"quality\" title=\"同じ要求が2箇所以上に表れているか\">変更可能性b</SPAN></a>に問題がある可能性があります。</SPAN><BR>");
        }
        out.writeln("---<BR>");
    }

    /**
     * Returns the similarity of the given two requirements sentences.
     * 
     * @return (req1 ∧ req2).size / (req1 ∨ req2).size.
     */
    private int similarity(final Requirement req1, final Requirement req2) {
        final Set<String> intersection = new HashSet<>(req1.getWords());
        intersection.retainAll(req2.getWords());

        final Set<String> union = new HashSet<>(req1.getWords());
        union.addAll(req2.getWords());

        if (union.isEmpty()) {
            return 0;
        } else {
            return intersection.size() * 100 / union.size();
        }
    }

}
