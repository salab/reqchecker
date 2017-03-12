package jp.ac.titech.cs.se.reqchecker.checker.traceability;

import java.io.IOException;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.checker.Checker;
import jp.ac.titech.cs.se.reqchecker.model.Requirement;

public class RequirementsNumberChecker implements Checker {

    private final Requirement requirement;

    public RequirementsNumberChecker(final Requirement requirement) {
        this.requirement = requirement;
    }

    @Override
    public boolean check() {
        return requirement.getNumber().equals("");
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        out.writeln("---<BR>");
        out.writeln("<SPAN class=\"traceability\">要求文に識別番号がありません。<BR>");
        out.writeln("<a href=\"quality/traceability.html\"><SPAN class=\"quality\" title=\"各要求にユニークな番号が振られているかどうか\">追跡可能性c</SPAN></a>に問題があります。</SPAN><BR>");
    }
}
