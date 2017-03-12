package jp.ac.titech.cs.se.reqchecker.checker.quality;

import java.io.IOException;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.checker.CheckerDelegate;
import jp.ac.titech.cs.se.reqchecker.checker.Tag;

public class Verifiability extends CheckerDelegate {
    public Verifiability(final Ambiguity ambiguity) {
        super(ambiguity, Tag.UNAMBIGUITY_D);
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        out.writeln("<B>あいまいな言葉・定性的な表現が</B>含まれています。<BR>");
        out.writeln("<B><a href=\"quality/unambiguity.html\"><SPAN class=\"quality\" title=\"判断基準があいまいな語句あるいは範囲や境界を表す語句が含まれているか\">非あいまい性dまたはe</SPAN></a>と関連</B>しています。<BR>");
        out.writeln("<a href=\"quality/verifiability.html\"><SPAN class=\"quality\" title=\"判断基準があいまいな語句あるいは定性的な表現が含まれているか\">検証可能性a</SPAN></a>に問題がある可能性があります。<BR>");
        out.writeln("---<BR>");
    }
}
