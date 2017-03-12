package jp.ac.titech.cs.se.reqchecker.checker.quality;

import jp.ac.titech.cs.se.reqchecker.checker.CompoundChecker;
import jp.ac.titech.cs.se.reqchecker.checker.modifiability.ReferenceSectionDetector;
import jp.ac.titech.cs.se.reqchecker.checker.modifiability.ReferringRelationDetector;
import jp.ac.titech.cs.se.reqchecker.checker.modifiability.SimilarRequirementFinder;
import jp.ac.titech.cs.se.reqchecker.model.Chapter;
import jp.ac.titech.cs.se.reqchecker.model.Document;

public class Modifiability extends CompoundChecker {
    private final Document document;

    public Modifiability(final Document document) {
        this.document = document;
    }

    @Override
    public void doCheck() {
        doCheck(new ReferenceSectionDetector(document));
        for (final Chapter chapter : document.getChapters()) {
            if (chapter.isRequirements()) {
                doCheck(new SimilarRequirementFinder(chapter));
            }
            doCheck(new ReferringRelationDetector(chapter));
        }
    }
}
