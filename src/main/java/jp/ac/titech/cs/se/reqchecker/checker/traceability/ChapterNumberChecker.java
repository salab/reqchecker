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
        out.writeln("<SPAN class=\"traceability\">章節番号がありません。<BR>");
        out.writeln("<a href=\"quality/traceability.html\"><SPAN class=\"quality\" title=\"章節番号が付与されているどうか\">追跡可能性a</SPAN></a>に問題があります。</SPAN><BR>");
        out.writeln("<H3>%s</H3>", chapter.getName());
    }
}
