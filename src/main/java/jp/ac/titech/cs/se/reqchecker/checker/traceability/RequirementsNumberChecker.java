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
        out.writeln("---<br>");
        out.writeln("<span class=\"traceability\">要求文に識別番号がありません。<br>");
        out.writeln("<a href=\"quality/traceability.html\"><span class=\"quality\" title=\"各要求にユニークな番号が振られているかどうか\">追跡可能性c</span></a>に問題があります。</span><br>");
    }
}
