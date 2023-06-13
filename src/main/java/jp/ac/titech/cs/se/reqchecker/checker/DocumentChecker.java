package jp.ac.titech.cs.se.reqchecker.checker;

import java.io.IOException;
import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.checker.consistency.DoubleDefinitionChecker;
import jp.ac.titech.cs.se.reqchecker.checker.quality.Completeness;
import jp.ac.titech.cs.se.reqchecker.checker.quality.Modifiability;
import jp.ac.titech.cs.se.reqchecker.checker.traceability.OverlappedNumberChecker;
import jp.ac.titech.cs.se.reqchecker.model.Chapter;
import jp.ac.titech.cs.se.reqchecker.model.Document;

public class DocumentChecker extends CompoundChecker {

    private final Document document;

    public DocumentChecker(final Document document) {
        this.document = document;
    }

    @Override
    public boolean check() {
        for (final Chapter chapter : document.getChapters()) {
            doCheck(new ChapterChecker(chapter));
        }
        doCheck(new OverlappedNumberChecker(document), Tag.TRACEABILITY);
        doCheck(new Modifiability(document), Tag.MODIFIABILITY);
        doCheck(new DoubleDefinitionChecker(document), Tag.CONSISTENCY);
        doCheck(new Completeness(document), Tag.COMPLETENESS);

        return true;
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        renderHeader(out);

        renderSubCheckers(out, "<hr>", Tag.DEFAULT);
        out.writeln("<hr>");
        renderSubCheckers(out, null, new Tag[] { Tag.TRACEABILITY, Tag.MODIFIABILITY, Tag.CONSISTENCY, Tag.COMPLETENESS });

        renderFooter(out);
    }

    private void renderHeader(final HTMLWriter out) throws IOException {
        out.writeln("<!DOCTYPE html>");
        out.writeln("<html>");
        out.writeln("<head>");
        out.writeln("<meta charset=\"UTF-8\">");
        // out.writeln("<link rel=stylesheet type=\"text/css\" href=\"output.css\">");
        out.writeln("<style>");
        out.writeln(".quality { color:#ff0000; background-color:#99ffff; font: bold 100% sans-serif; }");
        out.writeln(".section { font: bold 100% sans-serif; }");
        out.writeln(".modifier { background-color:#00ffff; }");
        out.writeln(".parallel { font: bold 100% sans-serif; background-color:#ff99ff; }");
        out.writeln(".lastParallel { font: bold 100% sans-serif; background-color:#ff6633; }");
        out.writeln(".word { font: bold 100% sans-serif; background-color:#99ff99; }");
        out.writeln(".requirement { font: bold 110% sans-serif; background-color:#A3D070; }");
        out.writeln(".reqSmall { font: bold 100% sans-serif; background-color:#D1FC9F; }");
        out.writeln("</style>");
        out.writeln("<title>reqchecker</title>");
        out.writeln("</head>");
        out.writeln("<body>");
    }

    private void renderFooter(final HTMLWriter out) throws IOException {
        out.writeln("</body>");
        out.writeln("</html>");
    }
}
