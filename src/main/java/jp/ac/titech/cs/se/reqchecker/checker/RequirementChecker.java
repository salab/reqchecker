package jp.ac.titech.cs.se.reqchecker.checker;

import java.io.IOException;
import jp.ac.titech.cs.se.reqchecker.HTMLWriter;
import jp.ac.titech.cs.se.reqchecker.checker.modifiability.InmodifiableDirectiveChecker;
import jp.ac.titech.cs.se.reqchecker.checker.quality.Ambiguity;
import jp.ac.titech.cs.se.reqchecker.checker.quality.Verifiability;
import jp.ac.titech.cs.se.reqchecker.checker.traceability.RequirementsNumberChecker;
import jp.ac.titech.cs.se.reqchecker.model.Requirement;
import jp.ac.titech.cs.se.reqchecker.model.Requirement.Type;

public class RequirementChecker extends CompoundChecker {

    private final Requirement requirement;

    private final boolean isInRequirementsSection;

    private final boolean isStructural;

    public RequirementChecker(final Requirement requirement, final boolean isInRequirementsSection, final boolean isStructural) {
        this.requirement = requirement;
        this.isInRequirementsSection = isInRequirementsSection;
        this.isStructural = isStructural;
    }

    @Override
    public boolean check() {
        if (isInRequirementsSection && isStructural) {
            doCheck(new RequirementsNumberChecker(requirement), Tag.TRACEABILITY);
            final Ambiguity ambiguity = new Ambiguity(requirement);
            doCheck(ambiguity, Tag.UNAMBIGUITY);
            doCheck(new Verifiability(ambiguity), Tag.VERIFIABILITY);
            doCheck(new InmodifiableDirectiveChecker(ambiguity), Tag.MODIFIABILITY);
        }
        return true;
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        if (isInRequirementsSection) {
            if (requirement.getType() != Type.CHAPTER) {
                out.writeln("---------------------------------------------<br>");
                out.writeln("<p>");
                if (!requirement.getType().isStructural()) {
                    renderRequirement(out);
                    renderSubCheckers(out, Tag.UNAMBIGUITY, Tag.VERIFIABILITY, Tag.MODIFIABILITY);
                    if (detectedCheckers.isEmpty()) {
                        out.writeln("<b>問題ない要求文である。</b><br>");
                    } else {
                        out.writeln("<b>修正が必要である可能性があります。</b><br>");
                    }
                } else if (requirement.getType() == Type.SECTION) {
                    out.writeln("<span class=\"section\">%s ", requirement.getNumber());
                    out.writeln("[%s]</span><br>", requirement.getRawSentence());
                }
                out.writeln("</p>");
            }
        } else {
            if (requirement.getType() == Type.OTHER) {
                out.writeln("[%s]<br>", requirement.getRawSentence());
            } else if (requirement.getType() == Type.SECTION) {
                out.writeln("<br>");
                out.writeln("<span class=\"section\">%s ", requirement.getNumber());
                out.writeln("[%s]</span><br>", requirement.getRawSentence());
            } else if (requirement.getType() == Type.REQ) {
                out.writeln("<b>");
                renderRequirement(out);
            }
        }
    }

    private void renderRequirement(final HTMLWriter out) throws IOException {
        if (!contains(Tag.TRACEABILITY)) {
            out.writeln("識別番号：</b>");
            out.writeln("<b id=\"%s\">%s</b><br>", requirement.getNumber(), requirement.getNumber());
        }
        out.writeln("<span%s>[%s]</span><br>", (isInRequirementsSection ? " class=\"requirement\"" : ""), requirement.getRawSentence());
        if (isInRequirementsSection) {
            renderSubCheckers(out, Tag.TRACEABILITY);
            out.writeln("---<br>");
        }
    }
}
