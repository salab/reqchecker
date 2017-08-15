package jp.ac.titech.cs.se.reqchecker.checker.quality;

import jp.ac.titech.cs.se.reqchecker.checker.CompoundChecker;
import jp.ac.titech.cs.se.reqchecker.checker.completeness.NoDefinitionSectionChecker;
import jp.ac.titech.cs.se.reqchecker.model.Document;

public class Completeness extends CompoundChecker {
    private final Document document;

    public Completeness(final Document document) {
        this.document = document;
    }

    @Override
    protected void doCheck() {
        doCheck(new NoDefinitionSectionChecker(document));
    }
}
