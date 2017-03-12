package jp.ac.titech.cs.se.reqchecker.checker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import jp.ac.titech.cs.se.reqchecker.HTMLWriter;

public abstract class CompoundChecker implements Checker {

    protected final List<Checker> detectedCheckers = new ArrayList<>();

    protected final ListMultimap<Tag, Checker> checkerMap = ArrayListMultimap.create();

    protected boolean doCheck(final Checker checker) {
        return doCheck(checker, Tag.DEFAULT);
    }

    protected boolean doCheck(final Checker checker, final Tag tag) {
        if (checker.check()) {
            detectedCheckers.add(checker);
            checkerMap.put(tag, checker);
            return true;
        }
        return false;
    }

    protected void doCheck() {
    }

    @Override
    public boolean check() {
        doCheck();
        return !detectedCheckers.isEmpty();
    }

    @Override
    public void render(final HTMLWriter out) throws IOException {
        renderSubCheckers(out, null, detectedCheckers);
    }

    protected void renderSubCheckers(final HTMLWriter out, final String separator, Tag... tags) throws IOException {
        final List<Checker> checkers = new ArrayList<>();
        for (final Tag tag : tags) {
            if (contains(tag)) {
                checkers.addAll(checkerMap.get(tag));
            }
        }
        renderSubCheckers(out, separator, checkers);
    }

    protected void renderSubCheckers(final HTMLWriter out, Tag... tags) throws IOException {
        renderSubCheckers(out, null, tags);
    }

    protected void renderSubCheckers(final HTMLWriter out, final String separator, final List<Checker> checkers) throws IOException {
        boolean first = true;
        for (final Checker checker : checkers) {
            if (!first && separator != null) {
                out.writeln(separator);
            }
            checker.render(out);
            first = false;
        }
    }

    public boolean contains(final Tag tag) {
        return checkerMap.containsKey(tag);
    }
}
