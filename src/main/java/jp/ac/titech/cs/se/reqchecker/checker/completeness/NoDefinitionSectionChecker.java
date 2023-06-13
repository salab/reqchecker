package jp.ac.titech.cs.se.reqchecker.checker.completeness;

import java.io.IOException;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.checker.AbstractDocumentChecker;
import jp.ac.titech.cs.se.reqchecker.model.Requirement;
import jp.ac.titech.cs.se.reqchecker.model.Document;
import jp.ac.titech.cs.se.reqchecker.model.Requirement.Type;

public class NoDefinitionSectionChecker extends AbstractDocumentChecker {

    public NoDefinitionSectionChecker(final Document document) {
        super(document);
    }

    @Override
    public boolean check() {
        for (final Requirement req : document.getRequirements()) {
            if (req.getType() == Type.CHAPTER && req.getRawSentence().contains("要求")) {
                break;
            } else if (req.getType().isStructural()) {
                final String raw = req.getRawSentence();
                if (raw.contains("用語") || raw.contains("定義")) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        out.writeln("<span class=\"traceability\">要求仕様書に<b>「用語」などの定義の項</b>がありません。<br>");
        out.writeln("<a href=\"quality/completeness.html\"><span class=\"quality\" title=\"要求仕様書に用語や単位の定義が記載されている\">完全性c1</span></a>に問題がある可能性があります。</span><br>");
        out.writeln("---<br>");
    }
}
