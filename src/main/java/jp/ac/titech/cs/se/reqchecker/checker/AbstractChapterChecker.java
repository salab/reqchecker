package jp.ac.titech.cs.se.reqchecker.checker;

import jp.ac.titech.cs.se.reqchecker.model.Chapter;

public abstract class AbstractChapterChecker implements Checker {
    protected final Chapter chapter;

    public AbstractChapterChecker(final Chapter chapter) {
        this.chapter = chapter;
    }
}
