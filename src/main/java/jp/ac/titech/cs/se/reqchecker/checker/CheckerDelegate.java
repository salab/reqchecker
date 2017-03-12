package jp.ac.titech.cs.se.reqchecker.checker;

public abstract class CheckerDelegate implements Checker {

    private final CompoundChecker checker;

    private final Tag tag;

    public CheckerDelegate(final CompoundChecker checker, final Tag tag) {
        this.checker = checker;
        this.tag = tag;
    }

    @Override
    public boolean check() {
        return checker.contains(tag);
    }
}
