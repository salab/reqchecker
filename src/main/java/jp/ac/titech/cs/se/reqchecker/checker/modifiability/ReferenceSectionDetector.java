package jp.ac.titech.cs.se.reqchecker.checker.modifiability;

import java.io.IOException;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.checker.AbstractDocumentChecker;
import jp.ac.titech.cs.se.reqchecker.model.Chapter;
import jp.ac.titech.cs.se.reqchecker.model.Document;

public class ReferenceSectionDetector extends AbstractDocumentChecker {

    public ReferenceSectionDetector(final Document document) {
        super(document);
    }

    @Override
    public boolean check() {
        for (final Chapter c : document.getChapters()) {
            if (c.getName().contains("索引")) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        out.writeln("<span class=\"traceability\">要求仕様書に<b>「索引」の章</b>がありません。<br>");
        out.writeln("<a href=\"quality/modifiability.html\"><span class=\"quality\" title=\"要求仕様書に索引が付けられているか\">変更可能性a</span></a>に問題があります。</span><br>");
        out.writeln("---<br>");
    }
}
