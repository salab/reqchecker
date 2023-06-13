package jp.ac.titech.cs.se.reqchecker.checker.traceability;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.checker.AbstractDocumentChecker;
import jp.ac.titech.cs.se.reqchecker.model.Document;
import jp.ac.titech.cs.se.reqchecker.model.Requirement;

public class OverlappedNumberChecker extends AbstractDocumentChecker {

    private final List<String> result = new ArrayList<>();

    public OverlappedNumberChecker(final Document document) {
        super(document);
    }

    @Override
    public boolean check() {
        final List<String> numbers = new ArrayList<>();
        for (final Requirement req : document.getRequirements()) {
            final String number = req.getNumber();
            if (numbers.contains(number)) {
                result.add(number);
            } else if (!number.equals("")) {
                numbers.add(number);
            }
        }
        return !result.isEmpty();
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        for (final String number : result) {
            out.writeln("識別番号<b><a href=\"#%s\"> [%s] </a>はかぶっています。</b><br>", number, number);
        }
        out.writeln("<a href=\"quality/traceability.html\"><span class=\"quality\" title=\"各要求にユニークな番号が振られているか\">追跡可能性b</span></a>に問題があります。<br>");
        out.writeln("---<br>");
    }
}
