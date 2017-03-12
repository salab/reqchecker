package jp.ac.titech.cs.se.reqchecker.checker;

import jp.ac.titech.cs.se.reqchecker.cabocha.Sentence;

public abstract class AbstractSentenceChecker implements Checker {

    protected final Sentence sentence;

    public AbstractSentenceChecker(final Sentence sentence) {
        this.sentence = sentence;
    }
}
