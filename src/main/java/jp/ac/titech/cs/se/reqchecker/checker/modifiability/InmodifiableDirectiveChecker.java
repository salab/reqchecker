package jp.ac.titech.cs.se.reqchecker.checker.modifiability;

import java.io.IOException;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.checker.CheckerDelegate;
import jp.ac.titech.cs.se.reqchecker.checker.Tag;
import jp.ac.titech.cs.se.reqchecker.checker.quality.Ambiguity;

public class InmodifiableDirectiveChecker extends CheckerDelegate {
    public InmodifiableDirectiveChecker(final Ambiguity ambiguity) {
        super(ambiguity, Tag.UNAMBIGUITY_C);
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        out.writeln("<b>要求文に指示語</b>が含まれているため、<b>指示語の参照先に依存</b>しています。<br>");
        out.writeln("<b><a href=\"quality/unambiguity.html\"><span class=\"quality\" title=\"指示語が含まれているか\">非あいまい性c</span></a>と関連</b>しています。<br>");
        out.writeln("<a href=\"quality/modifiability.html\"><span class=\"quality\" title=\"要求文が互いに依存していないかどうか\">変更可能性c</span></a>に問題がある可能性があります。<br>");
        out.writeln("---<br>");
    }

}
