package jp.ac.titech.cs.se.reqchecker.checker;

import java.io.IOException;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.checker.traceability.ChapterNumberChecker;
import jp.ac.titech.cs.se.reqchecker.model.Chapter;
import jp.ac.titech.cs.se.reqchecker.model.Requirement;

public class ChapterChecker extends CompoundChecker {

    private final Chapter chapter;

    public ChapterChecker(final Chapter chapter) {
        this.chapter = chapter;
    }

    @Override
    public boolean check() {
        for (final Requirement req : chapter.getRequirements()) {
            final boolean isReq = chapter.isRequirements();
            final boolean isStructural = !req.getType().isStructural();
            doCheck(new RequirementChecker(req, isReq, isStructural));
        }
        doCheck(new ChapterNumberChecker(chapter), Tag.TRACEABILITY);
        return true;
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        out.writeln("<DIV>");
        if (contains(Tag.TRACEABILITY)) {
            renderSubCheckers(out, Tag.TRACEABILITY);
        } else {
            out.writeln("<H3 id=\"%s\">%s %s</H3>", chapter.getNumber(), chapter.getNumber(), chapter.getName());
        }
        renderSubCheckers(out, Tag.DEFAULT);
        out.writeln("</DIV>");
    }
}
