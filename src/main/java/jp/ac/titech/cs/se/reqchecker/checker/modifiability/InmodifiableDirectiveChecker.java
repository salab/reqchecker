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
        out.writeln("<B>要求文に指示語</B>が含まれているため、<B>指示語の参照先に依存</B>しています。<BR>");
        out.writeln("<B><a href=\"quality/unambiguity.html\"><SPAN class=\"quality\" title=\"指示語が含まれているか\">非あいまい性c</SPAN></a>と関連</B>しています。<BR>");
        out.writeln("<a href=\"quality/modifiability.html\"><SPAN class=\"quality\" title=\"要求文が互いに依存していないかどうか\">変更可能性c</SPAN></a>に問題がある可能性があります。<BR>");
        out.writeln("---<BR>");
    }

}
