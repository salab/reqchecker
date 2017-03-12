package jp.ac.titech.cs.se.reqchecker.checker.completeness;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.checker.AbstractDocumentChecker;
import jp.ac.titech.cs.se.reqchecker.model.Definition;
import jp.ac.titech.cs.se.reqchecker.model.Document;
import jp.ac.titech.cs.se.reqchecker.util.AllPairs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class UndefinedPhraseChecker extends AbstractDocumentChecker {

    private final List<Pair<Definition, Definition>> result = new ArrayList<>();

    public UndefinedPhraseChecker(final Document document) {
        super(document);
    }

    @Override
    public boolean check() {
        for (final Pair<Definition, Definition> p : AllPairs.of(document.extractDefinitions())) {
            if (p.getLeft().matches(p.getRight())) {
                result.add(p);
            }
        }
        return !result.isEmpty();
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        for (final Pair<Definition, Definition> p : result) {
            final Definition def1 = p.getLeft();
            final Definition def2 = p.getRight();
            out.writeln("<B>未定義の言葉 [%s] または [%s] </B>が使われている可能性があります。</B><BR>", def1.getModifierCritical(), def2.getModifierCritical());
            out.writeln(def1.toHTML());
            out.writeln(def2.toHTML());
            out.writeln("<a href=\"quality/completeness.html\"><SPAN class=\"quality\" title=\"要求仕様書に未定義の語句があるか\">完全性c2</SPAN></a>に問題がある可能性があります。</SPAN><BR>");
        }
        out.writeln("---<BR>");
    }
}
