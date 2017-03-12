package jp.ac.titech.cs.se.reqchecker.checker.consistency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.checker.AbstractDocumentChecker;
import jp.ac.titech.cs.se.reqchecker.model.Chapter;
import jp.ac.titech.cs.se.reqchecker.model.Definition;
import jp.ac.titech.cs.se.reqchecker.model.Document;
import jp.ac.titech.cs.se.reqchecker.util.AllPairs;

public class DoubleDefinitionChecker extends AbstractDocumentChecker {

    private final List<Pair<Definition, Definition>> result = new ArrayList<>();

    public DoubleDefinitionChecker(final Document document) {
        super(document);
    }

    @Override
    public boolean check() {
        final List<Definition> definitions = new ArrayList<>();
        for (final Chapter chapter : document.getChapters()) {
            if (chapter.isRequirements()) {
                definitions.addAll(chapter.extractDefinitions());
            }
        }
        check(definitions);
        return !result.isEmpty();
    }

    private void check(final List<Definition> definitions) {
        for (final Pair<Definition, Definition> p : AllPairs.of(definitions)) {
            final Definition def1 = p.getLeft();
            final Definition def2 = p.getRight();
            if (def1.getSubject() == null || def2.getSubject() == null) {
                continue;
            } else if (def1.getModifier() == null || def2.getModifier() == null) {
                if (def1.matchesSubject(def2)) {
                    result.add(p);
                }
            } else {
                if (def1.matchesSubject(def2) && def1.matchesModifier(def2)) {
                    result.add(p);
                }
            }
        }
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        for (final Pair<Definition, Definition> p : result) {
            out.writeln("<B>二重定義</B>の可能性があります。<BR>");
            out.writeln(p.getLeft().toHTML());
            out.writeln(p.getRight().toHTML());
            out.writeln("<a href=\"quality/consistency.html\"><SPAN class=\"quality\" title=\"語句の定義が矛盾しているか\">無矛盾性b</SPAN></a>に問題がある可能性があります。</SPAN><BR>");
        }
        out.writeln("---<BR>");
    }
}
