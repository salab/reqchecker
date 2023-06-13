package jp.ac.titech.cs.se.reqchecker.checker.traceability;

import java.io.IOException;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.checker.Checker;
import jp.ac.titech.cs.se.reqchecker.model.Chapter;

public class ChapterNumberChecker implements Checker {
    private final Chapter chapter;

    public ChapterNumberChecker(final Chapter chapter) {
        this.chapter = chapter;
    }

    @Override
    public boolean check() {
        return chapter.getNumber().equals("");
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        out.writeln("<span class=\"traceability\">章節番号がありません。<br>");
        out.writeln("<a href=\"quality/traceability.html\"><span class=\"quality\" title=\"章節番号が付与されているどうか\">追跡可能性a</span></a>に問題があります。</span><br>");
        out.writeln("<h3>%s</h3>", chapter.getName());
    }
}
