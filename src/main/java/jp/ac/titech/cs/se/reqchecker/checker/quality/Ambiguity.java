package jp.ac.titech.cs.se.reqchecker.checker.quality;

import jp.ac.titech.cs.se.reqchecker.cabocha.Sentence;
import jp.ac.titech.cs.se.reqchecker.checker.CompoundChecker;
import jp.ac.titech.cs.se.reqchecker.checker.Tag;
import jp.ac.titech.cs.se.reqchecker.checker.ambiguity.AmbiguousWordChecker;
import jp.ac.titech.cs.se.reqchecker.checker.ambiguity.CoordinationDetector;
import jp.ac.titech.cs.se.reqchecker.checker.ambiguity.DirectiveWordChecker;
import jp.ac.titech.cs.se.reqchecker.checker.ambiguity.NoObjectChecker;
import jp.ac.titech.cs.se.reqchecker.checker.ambiguity.NoSubjectChecker;
import jp.ac.titech.cs.se.reqchecker.model.Requirement;

public class Ambiguity extends CompoundChecker {
    private final Requirement requirement;

    public Ambiguity(final Requirement requirement) {
        this.requirement = requirement;
    }

    @Override
    protected void doCheck() {
        final Sentence sentence = requirement.getSentence();
        doCheck(new CoordinationDetector(sentence), Tag.UNAMBIGUITY_A);
        doCheck(new NoSubjectChecker(sentence), Tag.UNAMBIGUITY_B);
        doCheck(new NoObjectChecker(sentence), Tag.UNAMBIGUITY_B);
        doCheck(new DirectiveWordChecker(sentence), Tag.UNAMBIGUITY_C);
        doCheck(new AmbiguousWordChecker(sentence), Tag.UNAMBIGUITY_D);
    }
}
