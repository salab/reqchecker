package jp.ac.titech.cs.se.reqchecker.checker;

import jp.ac.titech.cs.se.reqchecker.checker.Checker;
import jp.ac.titech.cs.se.reqchecker.model.Document;

public abstract class AbstractDocumentChecker implements Checker {
    protected final Document document;

    public AbstractDocumentChecker(final Document document) {
        this.document = document;
    }
}
