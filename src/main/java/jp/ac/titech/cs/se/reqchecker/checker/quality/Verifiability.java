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
        out.writeln("<b>あいまいな言葉・定性的な表現が</b>含まれています。<br>");
        out.writeln("<b><a href=\"quality/unambiguity.html\"><span class=\"quality\" title=\"判断基準があいまいな語句あるいは範囲や境界を表す語句が含まれているか\">非あいまい性dまたはe</span></a>と関連</b>しています。<br>");
        out.writeln("<a href=\"quality/verifiability.html\"><span class=\"quality\" title=\"判断基準があいまいな語句あるいは定性的な表現が含まれているか\">検証可能性a</span></a>に問題がある可能性があります。<br>");
        out.writeln("---<br>");
    }
}
